package com.rewards.command.command.service;


import com.cardservice.commons.event.TransactionEvent;
import com.rewards.command.command.model.RewardPoints;
import com.rewards.command.command.model.Transaction;
import com.rewards.command.command.producer.TransactionRewardEventProducer;
import com.rewards.command.command.repository.RewardPointsRepository;
import com.rewards.command.command.repository.TransactionRepository;
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
    private final TransactionRewardEventProducer transactionRewardEventProducer;

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

        // Publish transaction.reward event
        transactionRewardEventProducer.publishTransactionReward(transaction);

        log.info("Purchase processed successfully. Points earned: {}", pointsEarned);
    }

    /**
     * Processes a rollback event by reversing the points earned and updating the database
     */
    @Transactional
    public void processRollback(TransactionEvent rollbackEvent) {
        log.info("Processing rollback event: {}", rollbackEvent);

        // Find the transaction
        Optional<Transaction> transactionOpt = transactionRepository.findById(UUID.fromString(rollbackEvent.getId()));

        if (transactionOpt.isEmpty()) {
            log.error("Transaction not found for rollback: {}", rollbackEvent.getId());
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
            log.error("Reward points not found for rollback: client={}, card={}",
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

        // Publish transaction.reward event with rollback information
        transactionRewardEventProducer.publishTransactionRollback(transaction);

        log.info("Rollback processed successfully. Points reversed: {}", transaction.getPointsEarned());
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