package com.cardservice.command.event;

import com.cardservice.command.model.RefundRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class RefundEvent {

    private String transactionId;
    private String cardId;


    private RefundEvent(String transactionId, String cardId) {
        this.transactionId = transactionId;
        this.cardId = cardId;
    }


    public static RefundEvent createTransactionRefundedEventFrom(RefundRequest refundRequest) {
        return new RefundEvent(refundRequest.getTransactionId(),refundRequest.getCardId());
    }
}
