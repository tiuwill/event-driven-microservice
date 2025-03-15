package com.cardservice.query.repository;

import com.cardservice.commons.event.DisputeEvent;
import com.cardservice.commons.event.RefundEvent;
import com.cardservice.commons.event.TransactionEvent;
import com.cardservice.commons.event.TransactionRewardEvent;
import com.cardservice.commons.util.JsonParserUtil;
import com.cardservice.commons.event.RewardRefundEvent;
import com.cardservice.query.service.CardEventService;
import com.cardservice.query.service.RewardEventService;
import com.eventstore.dbclient.*;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventStoreConsumerService {

    private final EventStoreDBClient eventStoreDBClient;
    private static final String STREAM_NAME = "card-transactions"; // Change as needed
    private final CardEventService cardEventService;
    private final RewardEventService rewardEventService;

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
                        case "TransactionCreated" -> cardEventService.consumePurchaseEvent(JsonParserUtil.readValue(json, TransactionEvent.class));
                        case "TransactionDisputed" -> cardEventService.consumeDisputeEvent(JsonParserUtil.readValue(json, DisputeEvent.class));
                        case "TransactionRefunded" -> cardEventService.consumeRefundEvent(JsonParserUtil.readValue(json, RefundEvent.class));
                        case "TransactionRewarded" -> rewardEventService.consumeTransactionRewardEvent(JsonParserUtil.readValue(json, TransactionRewardEvent.class));
                        case "RewardRolledBack" -> rewardEventService.consumeTransactionRollbackRewardEvent(JsonParserUtil.readValue(json, RewardRefundEvent.class));
                        default -> {
                            return;
                        }
                    }
                }
            }).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
