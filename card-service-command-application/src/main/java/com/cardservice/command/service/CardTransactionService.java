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

        // Build the EventData object.
        EventData eventData = EventDataBuilder.json("TransactionCreated", JsonParserUtil.parseObjectToJson(transactionEvent)).build();

        // Append event to the "transaction-stream" stream.
        eventStore.appendToStream("card-"+transaction.getCardId(), eventData);
    }

    public void processRefund(RefundRequest refundRequest) {
        log.info("Processing refund for transaction: {}", refundRequest.getTransactionId());

        //TODO validar com projections - tentar pegar o card Id tamb
        //Deixar comentado para validar com projection
//        CardTransaction transaction = transactionRepository.findById(UUID.fromString(refundRequest.getTransactionId()))
//                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + refundRequest.getTransactionId()));
//
//        if (TransactionStatus.REFUNDED.equals(transaction.getStatus())) {
//            throw new RuntimeException("Transaction has already been refunded");
//        }

        RefundEvent refundEvent = RefundEvent.createTransactionRefundedEventFrom(refundRequest);
        EventData eventData = EventDataBuilder.json("TransactionRefunded", JsonParserUtil.parseObjectToJson(refundEvent)).build();

        eventStore.appendToStream("card-"+refundRequest.getCardId(), eventData);
    }

    public void processDispute(DisputeRequest disputeRequest) {
        log.info("Processing dispute for transaction: {}", disputeRequest.getTransactionId());


        //TODO mesmo caso do metodo anterior
//        CardTransaction transaction = transactionRepository.findById(UUID.fromString(disputeRequest.getTransactionId()))
//                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + disputeRequest.getTransactionId()));
//
//        if (transaction.getStatus() == TransactionStatus.DISPUTED) {
//            throw new RuntimeException("Transaction has already been disputed");
//        }
//
//        // Update transaction status
//        transaction.setStatus(TransactionStatus.DISPUTED);
//        transaction.setDisputeReason(disputeRequest.getReason());

//        // Save updated transaction
//        CardTransaction updatedTransaction = transactionRepository.save(transaction);
//
//        eventPublisher.publishDisputeEvent(updatedTransaction);

//        return updatedTransaction;

        DisputeEvent disputeEvent = DisputeEvent.createTransactionDisputedEventFrom(disputeRequest);
        EventData eventData = EventDataBuilder.json("TransactionDisputed", JsonParserUtil.parseObjectToJson(disputeEvent)).build();

        eventStore.appendToStream("card-"+disputeRequest.getCardId(), eventData);
    }

}
