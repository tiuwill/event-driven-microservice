package com.rewards.command.command.producer;

import com.rewards.command.command.model.RewardPoints;
import com.rewards.command.command.model.TotalRewardEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class TotalRewardEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topics.total-reward}")
    private String totalRewardTopic;

    public void publishTotalReward(RewardPoints rewardPoints) {
        TotalRewardEvent event = TotalRewardEvent.builder()
                .clientId(rewardPoints.getClientId())
                .cardId(rewardPoints.getCardId())
                .totalPoints(rewardPoints.getTotalPoints())
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send(totalRewardTopic, rewardPoints.getClientId().toString(), event);
        log.info("Published total reward event: {}", event);
    }
}