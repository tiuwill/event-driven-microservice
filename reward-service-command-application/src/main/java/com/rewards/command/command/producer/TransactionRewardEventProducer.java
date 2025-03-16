package com.rewards.command.command.producer;

import com.cardservice.commons.event.TransactionRewardEventBuilder;
import com.rewards.command.command.model.Transaction;
import com.cardservice.commons.event.TransactionRewardEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TransactionRewardEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.transaction-reward}")
    private String transactionRewardTopic;

    @Value("${kafka.topics.transaction-rollback-reward}")
    private String transactionRewardRollbackTopic;

    public void publishTransactionReward(Transaction transaction) {
        TransactionRewardEvent event = new TransactionRewardEventBuilder()
                .withTransactionId(transaction.getTransactionId().toString())
                .withClientId(transaction.getClientId().toString())
                .withCardId(transaction.getCardId().toString())
                .withAmount(transaction.getAmount())
                .withPointsEarned(transaction.getPointsEarned())
                .withTimestamp(transaction.getTimestamp())
                .withRollback(false)
                .build();

        kafkaTemplate.send(transactionRewardTopic, transaction.getTransactionId().toString(), event);
        log.info("Published transaction reward event: {}", event);
    }

    public void publishTransactionRollback(Transaction transaction) {
        TransactionRewardEvent event = new TransactionRewardEventBuilder()
                .withTransactionId(transaction.getTransactionId().toString())
                .withClientId(transaction.getClientId().toString())
                .withCardId(transaction.getCardId().toString())
                .withAmount(transaction.getAmount())
                .withPointsEarned(transaction.getPointsEarned())
                .withTimestamp(transaction.getTimestamp())
                .withRollback(true)
                .build();

        kafkaTemplate.send(transactionRewardRollbackTopic, transaction.getTransactionId().toString(), event);
        log.info("Published transaction rollback event: {}", event);
    }
}