package com.cardservice.command.service;



import com.cardservice.command.event.EventPublisher;
import com.cardservice.command.model.CardTransaction;
import com.cardservice.command.model.DisputeRequest;
import com.cardservice.command.model.RefundRequest;
import com.cardservice.command.repository.CardTransactionRepository;
import com.cardservice.commons.event.TransactionStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardTransactionService {

    private final CardTransactionRepository transactionRepository;
    private final EventPublisher eventPublisher;

    public CardTransaction processTransaction(CardTransaction transaction) {
        log.info("Processing transaction: {}", transaction);


        transaction.setStatus(TransactionStatus.APPROVED);

        // Save transaction
        CardTransaction savedTransaction = transactionRepository.save(transaction);

        // Publish transaction event
        eventPublisher.publishTransactionEvent(transaction);

        return savedTransaction;
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
