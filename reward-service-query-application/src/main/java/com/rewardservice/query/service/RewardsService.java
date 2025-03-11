package com.rewardservice.query.service;

import com.rewards.command.command.model.TransactionRewardEvent;
import com.rewardservice.query.model.CardRewards;
import com.rewardservice.query.model.RewardTransaction;
import com.rewardservice.query.repository.CardRewardsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RewardsService {

    private final CardRewardsRepository cardRewardsRepository;

    public void processRewardTransaction(TransactionRewardEvent event) {
        log.info("Processing reward transaction: {}", event.getTransactionId());

        RewardTransaction transaction = RewardTransaction.builder()
                .transactionId(event.getTransactionId())
                .cardId(event.getCardId())
                .customerId(event.getClientId())
                .amount(event.getAmount())
                .rewardPoints(event.getPointsEarned())
                .transactionDate(event.getTimestamp())
                .rolledBack(false)
                .build();

        CardRewards cardRewards = cardRewardsRepository.findById(event.getCardId())
                .orElse(CardRewards.builder()
                        .cardId(event.getCardId())
                        .customerId(event.getClientId())
                        .build());

        // Add transaction to the list
        cardRewards.getTransactions().add(transaction);

        // Save updated card rewards
        cardRewardsRepository.save(cardRewards);
        log.info("Reward transaction processed and saved: {}", event.getTransactionId());
    }

    public void processRollbackTransaction(TransactionRewardEvent event) {
        log.info("Processing rollback for transaction: {}", event.getTransactionId());

        CardRewards cardRewards = cardRewardsRepository.findById(event.getCardId())
                .orElse(null);

        if (cardRewards != null) {
            List<RewardTransaction> transactions = cardRewards.getTransactions();

            for (RewardTransaction transaction : transactions) {
                if (transaction.getTransactionId().equals(event.getTransactionId())) {
                    transaction.setRolledBack(true);
                    break;
                }
            }

            cardRewardsRepository.save(cardRewards);
            log.info("Transaction marked as rolled back: {}", event.getTransactionId());
        } else {
            log.warn("Card not found for rollback transaction: {}", event.getCardId());
        }
    }

    public CardRewards getCardRewards(String cardId) {
        return cardRewardsRepository.findById(cardId)
                .orElse(CardRewards.builder().cardId(cardId).build());
    }

}
