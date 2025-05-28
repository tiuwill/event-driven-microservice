package com.cardservice.commons.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RefundEvent extends TransactionEvent {

    public RefundEvent() {
    }

    public RefundEvent(String id, String cardId, String clientId, BigDecimal amount, String cnpj, LocalDateTime purchaseDate, String description) {
        super(id, cardId, clientId, amount, cnpj, TransactionStatus.REFUNDED.name(), purchaseDate, description);
    }
}
