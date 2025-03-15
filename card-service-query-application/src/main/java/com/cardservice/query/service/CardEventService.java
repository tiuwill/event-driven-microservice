package com.cardservice.query.service;


import com.cardservice.commons.event.DisputeEvent;
import com.cardservice.commons.event.RefundEvent;
import com.cardservice.commons.event.TransactionEvent;
import com.cardservice.commons.event.TransactionStatus;
import com.cardservice.query.dto.CardDetailViewData;
import com.cardservice.query.dto.InvoiceViewData;
import com.cardservice.query.repository.CardDetailViewRepository;
import com.cardservice.query.repository.InvoiceViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardEventService {

    private final CardDetailViewRepository cardDetailViewRepository;
    private final InvoiceViewRepository invoiceViewRepository;

    public void consumePurchaseEvent(TransactionEvent purchase) {
        log.info("Received purchase event: {}", purchase);
        updateCardDetailView(purchase);
        updateInvoiceView(purchase);
    }

    private void updateCardDetailView(TransactionEvent purchase) {
        CardDetailViewData cardDetailView = cardDetailViewRepository
                .findByCardIdAndClientId(purchase.getCardId(), purchase.getClientId())
                .orElse(new CardDetailViewData());

        if (cardDetailView.getPurchases() == null) {
            cardDetailView.setPurchases(new ArrayList<>());
        }

        cardDetailView.setCardId(purchase.getCardId());
        cardDetailView.setClientId(purchase.getClientId());

        CardDetailViewData.PurchaseDetail purchaseDetail = new CardDetailViewData.PurchaseDetail(
                purchase.getId(),
                purchase.getAmount(),
                purchase.getCnpj(),
                purchase.getPurchaseDate(),
                purchase.getDescription()
        );

        cardDetailView.getPurchases().add(purchaseDetail);
        cardDetailViewRepository.save(cardDetailView);
    }

    private void updateInvoiceView(TransactionEvent purchase) {
        InvoiceViewData invoiceView = invoiceViewRepository
                .findByCardId(purchase.getCardId())
                .orElse(new InvoiceViewData());

        if (invoiceView.getTransactions() == null) {
            invoiceView.setTransactions(new ArrayList<>());
            invoiceView.setTotalAmount(BigDecimal.ZERO);
        }

        invoiceView.setCardId(purchase.getCardId());
        invoiceView.setClientId(purchase.getClientId());
        invoiceView.setLastUpdateDate(LocalDateTime.now());

        // Update total amount
        BigDecimal currentTotal = invoiceView.getTotalAmount() != null ?
                invoiceView.getTotalAmount() : BigDecimal.ZERO;
        invoiceView.setTotalAmount(currentTotal.add(purchase.getAmount()));

        InvoiceViewData.TransactionDetail transactionDetail = new InvoiceViewData.TransactionDetail(
                purchase.getId(),
                purchase.getAmount(),
                purchase.getDescription(),
                purchase.getPurchaseDate(),
                purchase.getStatus()
        );

        invoiceView.getTransactions().add(transactionDetail);
        invoiceViewRepository.save(invoiceView);
    }


    public void consumeRefundEvent(RefundEvent refund) {
        log.info("Received refund event: {}", refund);

        InvoiceViewData invoiceView = invoiceViewRepository
                .findByCardId(refund.getCardId())
                .orElse(new InvoiceViewData());

        if (CollectionUtils.isEmpty(invoiceView.getTransactions())) {
            log.warn("No transactions found for cardId: {} and clientId: {}", refund.getCardId());
            return;
        }

        for (InvoiceViewData.TransactionDetail transaction : invoiceView.getTransactions()) {
            if (transaction.getId().equals(refund.getTransactionId())) {
                transaction.setDescription("Refund: " + transaction.getDescription());
                transaction.setTransactionDate(LocalDateTime.now());
                transaction.setStatus(TransactionStatus.REFUNDED.name());
                break;
            }
        }

        updateTotal(invoiceView);

        invoiceViewRepository.save(invoiceView);
    }

    public void consumeDisputeEvent(DisputeEvent dispute) {
        log.info("Received dispute event: {}", dispute);

        InvoiceViewData invoiceView = invoiceViewRepository
                .findByCardId(dispute.getCardId())
                .orElse(new InvoiceViewData());

        if (CollectionUtils.isEmpty(invoiceView.getTransactions())) {
            log.warn("No transactions found for cardId: {}", dispute.getCardId());
            return;
        }

        for (InvoiceViewData.TransactionDetail transaction : invoiceView.getTransactions()) {
            if (transaction.getId().equals(dispute.getTransactionId())) {
                transaction.setDescription("Disputed: " + transaction.getDescription());
                transaction.setTransactionDate(LocalDateTime.now());
                transaction.setStatus(TransactionStatus.DISPUTED.name());
                break;
            }
        }

        updateTotal(invoiceView);

        invoiceViewRepository.save(invoiceView);
    }

    private static void updateTotal(InvoiceViewData invoiceView) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (InvoiceViewData.TransactionDetail transaction : invoiceView.getTransactions()) {
            if(TransactionStatus.APPROVED.name().equals(transaction.getStatus()))
                totalAmount = totalAmount.add(transaction.getAmount());
        }
        invoiceView.setTotalAmount(totalAmount);
        invoiceView.setLastUpdateDate(LocalDateTime.now());
    }
}
