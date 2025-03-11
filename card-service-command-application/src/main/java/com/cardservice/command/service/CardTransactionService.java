package com.cardservice.command.service;

import com.fasterxml.jackson.databind.ObjectMapper;


import com.cardservice.command.event.EventPublisher;
import com.cardservice.command.model.CardTransaction;
import com.cardservice.command.model.DisputeRequest;
import com.cardservice.command.model.RefundRequest;
import com.cardservice.command.model.TransactionStatus;
import com.cardservice.command.repository.CardTransactionRepository;
import com.eventstore.dbclient.EventData;
import com.eventstore.dbclient.EventDataBuilder;
import com.eventstore.dbclient.EventStoreDBClient;

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
    private final EventPublisher eventPublisher;
    private final EventStoreDBClient eventStore;
    private final ObjectMapper objectMapper;

    public void processTransaction(CardTransaction transaction) {
        log.info("Processing transaction: {}", transaction);

        cardLimitService.deductLimit(transaction.getCardId(), transaction.getAmount());

        transaction.setId(UUID.randomUUID());
        transaction.setStatus(TransactionStatus.APPROVED);

        try {
            // Build the EventData object.
            EventData eventData = EventDataBuilder.json(UUID.randomUUID(), "TransactionCreated", objectMapper.writeValueAsBytes(transaction)).build();

            // Append event to the "transaction-stream" stream.
            eventStore.appendToStream("transaction-stream", eventData);
        } catch (Exception e) {
            log.error("Error publishing event", e);
            throw new RuntimeException("Error publishing event", e);
        }

    }

    public CardTransaction processRefund(RefundRequest refundRequest) {
        log.info("Processing refund for transaction: {}", refundRequest.getTransactionId());

        CardTransaction transaction = transactionRepository.findById(UUID.fromString(refundRequest.getTransactionId()))
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + refundRequest.getTransactionId()));

        if (transaction.getStatus() == TransactionStatus.REFUNDED) {
            throw new RuntimeException("Transaction has already been refunded");
        }

        // Update transaction status
        transaction.setStatus(TransactionStatus.REFUNDED);

        // Increase available limit
        cardLimitService.increaseLimit(transaction.getCardId(), transaction.getAmount());

        // Save updated transaction
        CardTransaction updatedTransaction = transactionRepository.save(transaction);

        // Publish refund event
        eventPublisher.publishRefundEvent(updatedTransaction);

        return updatedTransaction;
    }

    public CardTransaction processDispute(DisputeRequest disputeRequest) {
        log.info("Processing dispute for transaction: {}", disputeRequest.getTransactionId());

        CardTransaction transaction = transactionRepository.findById(UUID.fromString(disputeRequest.getTransactionId()))
                .orElseThrow(() -> new RuntimeException("Transaction not found with ID: " + disputeRequest.getTransactionId()));

        if (transaction.getStatus() == TransactionStatus.DISPUTED) {
            throw new RuntimeException("Transaction has already been disputed");
        }

        // Update transaction status
        transaction.setStatus(TransactionStatus.DISPUTED);
        transaction.setDisputeReason(disputeRequest.getReason());

        // Save updated transaction
        CardTransaction updatedTransaction = transactionRepository.save(transaction);

        eventPublisher.publishDisputeEvent(updatedTransaction);

        return updatedTransaction;
    }

}
