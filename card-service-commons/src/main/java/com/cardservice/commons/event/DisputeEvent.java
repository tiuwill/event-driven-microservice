package com.cardservice.commons.event;


import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DisputeEvent extends TransactionEvent {

    public DisputeEvent() {
    }

    public DisputeEvent(String id, String cardId, String clientId, BigDecimal amount, String cnpj, LocalDateTime purchaseDate, String description) {
        super(id, cardId, clientId, amount, cnpj, TransactionStatus.DISPUTED.name(), purchaseDate, description);
    }

}
