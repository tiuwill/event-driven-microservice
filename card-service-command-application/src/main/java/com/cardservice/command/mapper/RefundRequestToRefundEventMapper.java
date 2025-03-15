package com.cardservice.command.mapper;

import com.cardservice.command.model.RefundRequest;
import com.cardservice.commons.event.RefundEvent;

public class RefundRequestToRefundEventMapper {
    public static RefundEvent createTransactionRefundedEventFrom(RefundRequest refundRequest) {
        return new RefundEvent(refundRequest.getTransactionId(), refundRequest.getCardId());
    }
}
