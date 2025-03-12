package com.rewards.command.command.service;


import com.cardservice.command.event.DisputeEvent;
import com.cardservice.command.event.TransactionEvent;
import com.eventstore.dbclient.EventData;
import com.eventstore.dbclient.EventDataBuilder;
import com.eventstore.dbclient.EventStoreDBClient;
import com.rewards.command.command.model.*;
import com.rewards.command.command.repository.RewardPointsRepository;
import com.rewards.command.command.repository.TransactionRepository;
import com.rewards.command.command.util.JsonParserUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RewardsService {

    private final RewardPointsRepository rewardPointsRepository;
    private final TransactionRepository transactionRepository;
    private final EventStoreDBClient eventStore;

    private static final BigDecimal POINTS_THRESHOLD = new BigDecimal("5.00");

    /**
     * Processes a purchase event by calculating points and updating the database
     */
    @Transactional
    public void processPurchase(TransactionEvent purchaseEvent) {
        log.info("Processing purchase event: {}", purchaseEvent);

        // Calculate points (1 point for every 5 reais)
        Integer pointsEarned = calculatePoints(purchaseEvent.getAmount());

        // Get or create reward points for the client/card
        RewardPoints rewardPoints = getOrCreateRewardPoints(
                UUID.fromString(purchaseEvent.getClientId()),
                UUID.fromString(purchaseEvent.getCardId())
        );

        // Add points to the total
        rewardPoints.addPoints(pointsEarned);
        rewardPointsRepository.save(rewardPoints);

        // Save transaction
        Transaction transaction = new Transaction(
                UUID.fromString(purchaseEvent.getId()),
                UUID.fromString(purchaseEvent.getClientId()),
                UUID.fromString(purchaseEvent.getCardId()),
                purchaseEvent.getAmount(),
                pointsEarned
        );
        transactionRepository.save(transaction);

        TransactionRewarded transactionRewarded = TransactionRewarded.fromRewardedTransaction(transaction);
        EventData eventData = EventDataBuilder.json("TransactionRewarded", JsonParserUtil.parseObjectToJson(transactionRewarded)).build();

        eventStore.appendToStream("card-"+purchaseEvent.getCardId(), eventData);
        log.info("Purchase processed successfully. Points earned: {}", pointsEarned);
    }

    /**
     * Processes a rollback event by reversing the points earned and updating the database
     */
    @Transactional
    public void processDispute(TransactionDisputeEvent rollbackEvent) {
        log.info("Processing dispute event: {}", rollbackEvent);

        // Find the transaction
        Optional<Transaction> transactionOpt = transactionRepository.findById(UUID.fromString(rollbackEvent.getTransactionId()));

        if (transactionOpt.isEmpty()) {
            log.error("Transaction not found for dispute: {}", rollbackEvent.getTransactionId());
            return;
        }

        Transaction transaction = transactionOpt.get();

        // If already rolled back, do nothing
        if (transaction.isRollback()) {
            log.warn("Transaction already disputed: {}", transaction.getTransactionId());
            return;
        }

        // Find reward points
        Optional<RewardPoints> rewardPointsOpt = rewardPointsRepository.findByClientIdAndCardId(
                transaction.getClientId(),
                transaction.getCardId()
        );

        if (rewardPointsOpt.isEmpty()) {
            log.error("Reward points not found to dispute: client={}, card={}",
                    transaction.getClientId(),
                    transaction.getCardId());
            return;
        }

        RewardPoints rewardPoints = rewardPointsOpt.get();

        // Subtract points
        rewardPoints.subtractPoints(transaction.getPointsEarned());
        rewardPointsRepository.save(rewardPoints);

        // Mark transaction as rolled back
        transaction.setRollback(true);
        transactionRepository.save(transaction);

        TransactionRewarded transactionRewarded = TransactionRewarded.fromRewardedTransaction(transaction);
        EventData eventData = EventDataBuilder.json("TransactionRewarded", JsonParserUtil.parseObjectToJson(transactionRewarded)).build();

        eventStore.appendToStream("card-"+rollbackEvent.getCardId(), eventData);


        log.info("Transaction dispute processed successfully. Points reversed: {}", transaction.getPointsEarned());
    }

    @Transactional
    public void processRefund(TransactionRefundEvent rollbackEvent) {
        log.info("Processing refund event: {}", rollbackEvent);

        // Find the transaction
        Optional<Transaction> transactionOpt = transactionRepository.findById(UUID.fromString(rollbackEvent.getTransactionId()));

        if (transactionOpt.isEmpty()) {
            log.error("Transaction not found for refund: {}", rollbackEvent.getTransactionId());
            return;
        }

        Transaction transaction = transactionOpt.get();

        // If already rolled back, do nothing
        if (transaction.isRollback()) {
            log.warn("Transaction already rolled back: {}", transaction.getTransactionId());
            return;
        }

        // Find reward points
        Optional<RewardPoints> rewardPointsOpt = rewardPointsRepository.findByClientIdAndCardId(
                transaction.getClientId(),
                transaction.getCardId()
        );

        if (rewardPointsOpt.isEmpty()) {
            log.error("Reward points not found to refund: client={}, card={}",
                    transaction.getClientId(),
                    transaction.getCardId());
            return;
        }

        RewardPoints rewardPoints = rewardPointsOpt.get();

        // Subtract points
        rewardPoints.subtractPoints(transaction.getPointsEarned());
        rewardPointsRepository.save(rewardPoints);

        // Mark transaction as rolled back
        transaction.setRollback(true);
        transactionRepository.save(transaction);

        TransactionRefundEvent transactionRewarded = TransactionRefundEvent.fromRewardedTransaction(transaction);
        EventData eventData = EventDataBuilder.json("TransactionRefunded", JsonParserUtil.parseObjectToJson(transactionRewarded)).build();

        eventStore.appendToStream("card-"+rollbackEvent.getCardId(), eventData);


        log.info("Refund processed successfully. Points reversed: {}", transaction.getPointsEarned());
    }

    /**
     * Calculate points based on purchase amount (1 point for every 5 reais)
     */
    private Integer calculatePoints(BigDecimal amount) {
        return amount.divide(POINTS_THRESHOLD, 0, RoundingMode.DOWN).intValue();
    }

    /**
     * Get existing reward points or create new ones if they don't exist
     */
    private RewardPoints getOrCreateRewardPoints(UUID clientId, UUID cardId) {
        return rewardPointsRepository.findByClientIdAndCardId(clientId, cardId)
                .orElseGet(() -> {
                    RewardPoints newRewardPoints = new RewardPoints(clientId, cardId);
                    return rewardPointsRepository.save(newRewardPoints);
                });
    }
}