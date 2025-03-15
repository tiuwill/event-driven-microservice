package com.cardservice.command.mapper;

import com.cardservice.command.model.DisputeRequest;
import com.cardservice.commons.event.DisputeEvent;

public class DisputeRequestMapper {
    public static DisputeEvent createTransactionDisputedEventFrom(DisputeRequest disputeRequest) {
        return new DisputeEvent(disputeRequest.getCardId(), disputeRequest.getTransactionId(), disputeRequest.getReason());
    }
}