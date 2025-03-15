package com.cardservice.command.mapper;

import com.cardservice.command.model.CardTransaction;
import com.cardservice.commons.event.TransactionEvent;
import com.cardservice.commons.event.TransactionStatus;

import java.util.UUID;

public class TransactionEventMapper {

    public static TransactionEvent createTransactionCreatedEventFrom(CardTransaction cardTransaction) {
        return new TransactionEvent(
                UUID.randomUUID().toString(),
                cardTransaction.getCardId().toString(),
                cardTransaction.getCustomerId().toString(),
                cardTransaction.getAmount(),
                cardTransaction.getCnpj(),
                TransactionStatus.APPROVED.name(),
                cardTransaction.getTransactionDate(),
                cardTransaction.getDescription()
        );
    }


}
