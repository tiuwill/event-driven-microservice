package com.cardservice.command.service;

import com.cardservice.command.event.DisputeEvent;
import com.cardservice.command.event.RefundEvent;
import com.cardservice.command.event.TransactionEvent;
import com.cardservice.command.util.JsonParserUtil;
import com.eventstore.dbclient.*;

import com.cardservice.command.model.CardTransaction;
import com.cardservice.command.model.DisputeRequest;
import com.cardservice.command.model.RefundRequest;
import com.cardservice.command.model.TransactionStatus;
import com.cardservice.command.repository.CardTransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardTransactionService {

    private final CardTransactionRepository transactionRepository;
    private final CardLimitService cardLimitService;
    private final EventStoreDBClient eventStore;

    public void processTransaction(CardTransaction transaction) {
        log.info("Processing transaction: {}", transaction);

        cardLimitService.deductLimit(transaction.getCardId(), transaction.getAmount());

        transaction.setId(UUID.randomUUID());
        transaction.setStatus(TransactionStatus.APPROVED);

        TransactionEvent transactionEvent = TransactionEvent.createTransactionCreatedEventFrom(transaction);

        EventData eventData = EventDataBuilder.json("TransactionCreated", JsonParserUtil.parseObjectToJson(transactionEvent)).build();

        eventStore.appendToStream("card-" + transaction.getCardId(), eventData);
        eventStore.appendToStream("card-transactions", eventData);
    }

    public void processRefund(RefundRequest refundRequest) {
        log.info("Processing refund for transaction: {}", refundRequest.getTransactionId());

        RefundEvent refundEvent = RefundEvent.createTransactionRefundedEventFrom(refundRequest);
        EventData eventData = EventDataBuilder.json("TransactionRefunded", JsonParserUtil.parseObjectToJson(refundEvent)).build();

        eventStore.appendToStream("card-" + refundRequest.getCardId(), eventData);
        eventStore.appendToStream("card-transactions", eventData);
    }

    public void processDispute(DisputeRequest disputeRequest) {
        log.info("Processing dispute for transaction: {}", disputeRequest.getTransactionId());

        DisputeEvent disputeEvent = DisputeEvent.createTransactionDisputedEventFrom(disputeRequest);
        EventData eventData = EventDataBuilder.json("TransactionDisputed", JsonParserUtil.parseObjectToJson(disputeEvent)).build();

        eventStore.appendToStream("card-" + disputeRequest.getCardId(), eventData);
        eventStore.appendToStream("card-transactions", eventData);
    }

}
