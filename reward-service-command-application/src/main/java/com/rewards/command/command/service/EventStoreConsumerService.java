package com.rewards.command.command.service;

import com.cardservice.command.event.DisputeEvent;
import com.cardservice.command.event.RefundEvent;
import com.cardservice.command.event.TransactionEvent;
import com.eventstore.dbclient.EventStoreDBClient;
import com.eventstore.dbclient.ResolvedEvent;
import com.eventstore.dbclient.Subscription;
import com.eventstore.dbclient.SubscriptionListener;
import com.rewards.command.command.util.JsonParserUtil;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class EventStoreConsumerService {

    private final EventStoreDBClient eventStoreDBClient;
    private static final String STREAM_NAME = "card-transactions";
    private final RewardsService rewardsService;

    @PostConstruct
    public void startSubscription() {
        try {
            eventStoreDBClient.subscribeToStream(STREAM_NAME, new SubscriptionListener() {
                @Override
                public void onEvent(Subscription subscription, ResolvedEvent event) {
                    String eventType = event.getOriginalEvent().getEventType();
                    byte[] eventDataBytes = event.getOriginalEvent().getEventData();
                    String eventDataJson = new String(eventDataBytes);
                    String json = JsonParserUtil.readValue(eventDataJson, String.class);
;

                    switch (eventType) {
                        case "TransactionCreated" -> rewardsService.processPurchase(JsonParserUtil.readValue(json, TransactionEvent.class));
                        case "TransactionDisputed" -> rewardsService.processDispute(JsonParserUtil.readValue(json, DisputeEvent.class));
                        case "TransactionRefunded" -> rewardsService.processRefund(JsonParserUtil.readValue(json, RefundEvent.class));
                        default -> {}
                    }
                }
            }).get(); // Wait for subscription
        } catch (Exception e) {
            log.error("Error starting subscription: {}", e.getMessage());
            throw new RuntimeException("Error starting subscription", e);
        }
    }
}
