package com.rewards.command.command.consumer;

import com.cardservice.command.event.TransactionEvent;
import com.rewards.command.command.model.PurchaseEvent;
import com.rewards.command.command.service.RewardsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PurchaseEventConsumer {

    private final RewardsService rewardsService;

    @KafkaListener(topics = "${kafka.topics.purchase-event}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(TransactionEvent purchaseEvent) {
        log.info("Received purchase event: {}", purchaseEvent);
        try {
            rewardsService.processPurchase(purchaseEvent);
        } catch (Exception e) {
            log.error("Error processing purchase event: {}", e.getMessage(), e);
        }
    }
}