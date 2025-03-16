package com.cardservice.command.mapper;

import com.cardservice.command.model.CardTransaction;
import com.cardservice.commons.event.DisputeEvent;

public class DisputeEventMapper {

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
