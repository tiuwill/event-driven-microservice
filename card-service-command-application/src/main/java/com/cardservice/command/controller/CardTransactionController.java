package com.cardservice.command.controller;


import com.cardservice.command.model.CardTransaction;
import com.cardservice.command.model.DisputeRequest;
import com.cardservice.command.model.RefundRequest;
import com.cardservice.command.service.CardTransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/card-transactions")
public class CardTransactionController {

    private final CardTransactionService transactionService;

    @PostMapping
    public ResponseEntity<CardTransaction> processTransaction(@Valid @RequestBody CardTransaction transaction) {
        log.info("Received transaction request: {}", transaction);
        CardTransaction processedTransaction = transactionService.processTransaction(transaction);
        return new ResponseEntity<>(processedTransaction, HttpStatus.CREATED);
    }

    @PostMapping("/refund")
    public ResponseEntity<CardTransaction> processRefund(@Valid @RequestBody RefundRequest refundRequest) {
        log.info("Received refund request: {}", refundRequest);
        CardTransaction refundedTransaction = transactionService.processRefund(refundRequest);
        return new ResponseEntity<>(refundedTransaction, HttpStatus.OK);
    }

    @PostMapping("/dispute")
    public ResponseEntity<CardTransaction> processDispute(@Valid @RequestBody DisputeRequest disputeRequest) {
        log.info("Received dispute request: {}", disputeRequest);
        CardTransaction disputedTransaction = transactionService.processDispute(disputeRequest);
        return new ResponseEntity<>(disputedTransaction, HttpStatus.OK);
    }
}
