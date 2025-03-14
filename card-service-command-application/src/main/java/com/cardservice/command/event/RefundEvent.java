package com.cardservice.command.event;

import com.cardservice.command.model.RefundRequest;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundEvent {

    private String transactionId;
    private String cardId;



    public static RefundEvent createTransactionRefundedEventFrom(RefundRequest refundRequest) {
        return new RefundEvent(refundRequest.getTransactionId(),refundRequest.getCardId());
    }
}
