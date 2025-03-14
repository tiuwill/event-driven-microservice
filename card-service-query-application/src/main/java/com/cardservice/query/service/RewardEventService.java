package com.cardservice.query.service;


import com.cardservice.query.dto.InvoiceViewData;
import com.cardservice.query.event.RewardRefundEvent;
import com.cardservice.query.repository.InvoiceViewRepository;
import com.rewards.command.command.model.TransactionRewardEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RewardEventService {

    private final InvoiceViewRepository invoiceViewRepository;

    public void consumeTransactionRewardEvent(TransactionRewardEvent reward) {
        log.info("Received transaction reward event: {}", reward);

        Optional<InvoiceViewData> optionalInvoiceView = Optional.empty();
        int retryCount = 0;
        while (optionalInvoiceView.isEmpty() && retryCount < 3) {
            optionalInvoiceView = invoiceViewRepository
                    .findByCardId(reward.getCardId());
            if (optionalInvoiceView.isEmpty()) {
                log.warn("Invoice view not found for card: {}, retrying...", reward.getCardId());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                retryCount++;
            }
        }

        InvoiceViewData invoiceView = optionalInvoiceView.orElse(null);
        if (invoiceView != null) {
            if(invoiceView.getTotalRewardPoints() == null) {
                invoiceView.setTotalRewardPoints(0);
            }
            invoiceView.setTotalRewardPoints(invoiceView.getTotalRewardPoints() + reward.getAmount());
            invoiceView.setLastUpdateDate(reward.getRewardedTime());
            invoiceViewRepository.save(invoiceView);
            log.info("Incremented reward points for card: {}", reward.getCardId());
        } else {
            log.warn("No invoice view found for card: {}", reward.getCardId());
        }
    }

    public void consumeTransactionRollbackRewardEvent(RewardRefundEvent reward) {
        log.info("Received transaction rollback reward event: {}", reward);

        InvoiceViewData invoiceView = invoiceViewRepository
                .findByCardId(reward.getCardId())
                .orElse(null);

        if (invoiceView != null) {
            invoiceView.setTotalRewardPoints(invoiceView.getTotalRewardPoints() - reward.getAmount());
            invoiceView.setLastUpdateDate(reward.getRollbackTime());
            invoiceViewRepository.save(invoiceView);
            log.info("Decremented reward points for card: {}", reward.getCardId());
        } else {
            log.warn("No invoice view found for card: {}", reward.getCardId());
        }
    }

}
