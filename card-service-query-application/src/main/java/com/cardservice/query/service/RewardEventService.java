package com.cardservice.query.service;


import com.cardservice.commons.event.TransactionRewardEvent;
import com.cardservice.query.dto.InvoiceViewData;
import com.cardservice.query.repository.InvoiceViewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RewardEventService {

    private final InvoiceViewRepository invoiceViewRepository;

    @KafkaListener(topics = "${kafka.topics.transaction-reward}")
    public void consumeTransactionRewardEvent(TransactionRewardEvent reward) {
        log.info("Received transaction reward event: {}", reward);

        Optional<InvoiceViewData> optionalInvoiceView = Optional.empty();
        int retryCount = 0;
        while (optionalInvoiceView.isEmpty() && retryCount < 3) {
            optionalInvoiceView = invoiceViewRepository
                    .findByCardIdAndClientId(reward.getCardId(), reward.getClientId());
            if (optionalInvoiceView.isEmpty()) {
                log.warn("Invoice view not found for card: {}, client: {}, retrying...", reward.getCardId(), reward.getClientId());
                try {
                    Thread.sleep(1000); // Wait for 1 second before retrying
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
            invoiceView.setTotalRewardPoints(invoiceView.getTotalRewardPoints() + reward.getPointsEarned());
            invoiceView.setLastUpdateDate(reward.getTimestamp());
            invoiceViewRepository.save(invoiceView);
            log.info("Incremented reward points for card: {}, client: {}", reward.getCardId(), reward.getClientId());
        } else {
            log.warn("No invoice view found for card: {}, client: {}", reward.getCardId(), reward.getClientId());
        }
    }

    @KafkaListener(topics = "${kafka.topics.transaction-rollback-reward}")
    public void consumeTransactionRollbackRewardEvent(TransactionRewardEvent reward) {
        log.info("Received transaction rollback reward event: {}", reward);

        InvoiceViewData invoiceView = invoiceViewRepository
                .findByCardIdAndClientId(reward.getCardId(), reward.getClientId())
                .orElse(null);

        if (invoiceView != null) {
            invoiceView.setTotalRewardPoints(invoiceView.getTotalRewardPoints() - reward.getPointsEarned());
            invoiceView.setLastUpdateDate(reward.getTimestamp());
            invoiceViewRepository.save(invoiceView);
            log.info("Decremented reward points for card: {}, client: {}", reward.getCardId(), reward.getClientId());
        } else {
            log.warn("No invoice view found for card: {}, client: {}", reward.getCardId(), reward.getClientId());
        }
    }

}
