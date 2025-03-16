package com.cardservice.command.mapper;

import com.cardservice.command.model.CardTransaction;
import com.cardservice.commons.event.RefundEvent;

public class RefundEventMapper {

    public static RefundEvent fromCardTransaction(CardTransaction cardTransaction) {
        return new RefundEvent(
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
