package com.rewards.command.command.consumer;

import com.cardservice.command.event.DisputeEvent;
import com.cardservice.command.event.RefundEvent;
import com.rewards.command.command.service.RewardsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RollbackEventConsumer {

    private final RewardsService rewardsService;
//
//    @KafkaListener(topics = "${kafka.topics.rollback-event}", groupId = "${spring.kafka.consumer.group-id}")
//    public void consume(RefundEvent rollbackEvent) {
//        log.info("Received rollback event: {}", rollbackEvent);
//        try {
//            rewardsService.processRollback(rollbackEvent);
//        } catch (Exception e) {
//            log.error("Error processing rollback event: {}", e.getMessage(), e);
//        }
//    }
//
//    @KafkaListener(topics = "${kafka.topic.dispute}", groupId = "${spring.kafka.consumer.group-id}")
//    public void dispute(DisputeEvent rollbackEvent) {
//        log.info("Received rollback event: {}", rollbackEvent);
//        try {
//            rewardsService.processRollback(rollbackEvent);
//        } catch (Exception e) {
//            log.error("Error processing rollback event: {}", e.getMessage(), e);
//        }
//    }
}
