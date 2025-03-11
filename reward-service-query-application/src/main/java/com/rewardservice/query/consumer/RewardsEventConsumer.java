package com.rewardservice.query.consumer;

import com.rewards.command.command.model.TransactionRewardEvent;
import com.rewardservice.query.service.RewardsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RewardsEventConsumer {

    private final RewardsService rewardsService;

    @KafkaListener(topics = "${kafka.topics.transaction-reward}")
    public void consumeTransactionRewardEvent(TransactionRewardEvent event) {
        log.info("Received transaction reward event: {}", event.getTransactionId());
        rewardsService.processRewardTransaction(event);
    }

    @KafkaListener(topics = "${kafka.topics.transaction-rollback}")
    public void consumeTransactionRollbackEvent(TransactionRewardEvent event) {
        log.info("Received transaction rollback event: {}", event.getTransactionId());
        rewardsService.processRollbackTransaction(event);
    }

}