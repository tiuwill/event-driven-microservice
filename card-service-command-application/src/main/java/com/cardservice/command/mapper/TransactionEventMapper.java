package com.cardservice.command.mapper;

import com.cardservice.command.model.CardTransaction;
import com.cardservice.commons.event.TransactionEvent;

public class TransactionEventMapper {

    public static TransactionEvent fromCardTransaction(CardTransaction cardTransaction) {
        return new TransactionEvent(
                cardTransaction.getId().toString(),
                cardTransaction.getCardId().toString(),
                cardTransaction.getCustomerId().toString(),
                cardTransaction.getAmount(),
                cardTransaction.getCnpj(),
                cardTransaction.getStatus().name(),
                cardTransaction.getTransactionDate(),
                cardTransaction.getDescription()
        );
    }
}