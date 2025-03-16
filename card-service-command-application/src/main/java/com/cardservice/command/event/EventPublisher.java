package com.cardservice.command.event;

import com.cardservice.command.mapper.DisputeEventMapper;
import com.cardservice.command.mapper.RefundEventMapper;
import com.cardservice.command.mapper.TransactionEventMapper;
import com.cardservice.command.model.CardTransaction;
import com.cardservice.commons.event.DisputeEvent;
import com.cardservice.commons.event.RefundEvent;
import com.cardservice.commons.event.TransactionEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.messaging.Message;


@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${kafka.topic.transaction}")
    private String transactionTopic;

    @Value("${kafka.topic.refund}")
    private String refundTopic;

    @Value("${kafka.topic.dispute}")
    private String disputeTopic;

    public void publishTransactionEvent(CardTransaction transaction) {
        log.info("Publishing transaction event: {}", transaction);

        Message<TransactionEvent> message = MessageBuilder
                .withPayload(TransactionEventMapper.fromCardTransaction(transaction))
                .setHeader(KafkaHeaders.TOPIC, transactionTopic)
                .build();

        kafkaTemplate.send(message);
    }

    public void publishRefundEvent(CardTransaction event) {
        log.info("Publishing refund event: {}", event);

        Message<RefundEvent> message = MessageBuilder
                .withPayload(RefundEventMapper.fromCardTransaction(event))
                .setHeader(KafkaHeaders.TOPIC, refundTopic)
                .build();

        kafkaTemplate.send(message);
    }

    public void publishDisputeEvent(CardTransaction event) {
        log.info("Publishing dispute event: {}", event);

        Message<DisputeEvent> message = MessageBuilder
                .withPayload(DisputeEventMapper.fromCardTransaction(event))
                .setHeader(KafkaHeaders.TOPIC, disputeTopic)
                .build();

        kafkaTemplate.send(message);
    }
}