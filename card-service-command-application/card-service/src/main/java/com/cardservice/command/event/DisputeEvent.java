package com.cardservice.command.event;

import com.cardservice.command.model.CardTransaction;
import com.cardservice.command.model.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DisputeEvent extends TransactionEvent {

    private DisputeEvent(String id, String cardId, String clientId, BigDecimal amount, String cnpj, LocalDateTime purchaseDate, String description) {
        super(id, cardId, clientId, amount, cnpj, TransactionStatus.DISPUTED.name(), purchaseDate, description);
    }

    public static DisputeEvent fromCardTransaction(CardTransaction cardTransaction) {
        return new DisputeEvent(
                cardTransaction.getId().toString(),
                cardTransaction.getCardId().toString(),
                cardTransaction.getCustomerId().toString(),
                cardTransaction.getAmount(),
                cardTransaction.getCnpj(),
                cardTransaction.getTransactionDate(),
                cardTransaction.getDescription()
        );
    }

}
