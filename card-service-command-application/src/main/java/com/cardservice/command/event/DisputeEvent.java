package com.cardservice.command.event;

import com.cardservice.command.model.CardTransaction;
import com.cardservice.command.model.DisputeRequest;
import com.cardservice.command.model.TransactionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class DisputeEvent {

    private String cardId;
    private String transactionId;
    private String reason;

    public DisputeEvent(String cardId, String transactionId, String reason) {
        this.cardId = cardId;
        this.transactionId = transactionId;
        this.reason = reason;
    }


    public static DisputeEvent createTransactionDisputedEventFrom(DisputeRequest disputeRequest) {
        return new DisputeEvent(disputeRequest.getCardId(), disputeRequest.getTransactionId(), disputeRequest.getReason());
    }

}
