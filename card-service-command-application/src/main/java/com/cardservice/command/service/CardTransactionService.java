package com.cardservice.command.service;

import com.cardservice.command.mapper.DisputeRequestMapper;
import com.cardservice.command.mapper.RefundRequestToRefundEventMapper;
import com.cardservice.command.mapper.TransactionEventMapper;
import com.cardservice.commons.event.DisputeEvent;
import com.cardservice.commons.event.RefundEvent;
import com.cardservice.commons.event.TransactionEvent;
import com.cardservice.commons.event.TransactionStatus;
import com.cardservice.commons.util.JsonParserUtil;
import com.eventstore.dbclient.*;

import com.cardservice.command.model.CardTransaction;
import com.cardservice.command.model.DisputeRequest;
import com.cardservice.command.model.RefundRequest;
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
    private final EventStoreDBClient eventStore;

    public void processTransaction(CardTransaction transaction) {
        log.info("Processing transaction: {}", transaction);

        transaction.setId(UUID.randomUUID());
        transaction.setStatus(TransactionStatus.APPROVED);

        TransactionEvent transactionEvent = TransactionEventMapper.createTransactionCreatedEventFrom(transaction);

        EventData eventData = EventDataBuilder.json("TransactionCreated", JsonParserUtil.parseObjectToJson(transactionEvent)).build();

        eventStore.appendToStream("card-" + transaction.getCardId(), eventData);
        eventStore.appendToStream("card-transactions", eventData);
    }

    public void processRefund(RefundRequest refundRequest) {
        log.info("Processing refund for transaction: {}", refundRequest.getTransactionId());

        RefundEvent refundEvent = RefundRequestToRefundEventMapper.createTransactionRefundedEventFrom(refundRequest);
        EventData eventData = EventDataBuilder.json("TransactionRefunded", JsonParserUtil.parseObjectToJson(refundEvent)).build();

        eventStore.appendToStream("card-" + refundRequest.getCardId(), eventData);
        eventStore.appendToStream("card-transactions", eventData);
    }

    public void processDispute(DisputeRequest disputeRequest) {
        log.info("Processing dispute for transaction: {}", disputeRequest.getTransactionId());

        DisputeEvent disputeEvent = DisputeRequestMapper.createTransactionDisputedEventFrom(disputeRequest);
        EventData eventData = EventDataBuilder.json("TransactionDisputed", JsonParserUtil.parseObjectToJson(disputeEvent)).build();

        eventStore.appendToStream("card-" + disputeRequest.getCardId(), eventData);
        eventStore.appendToStream("card-transactions", eventData);
    }

}
