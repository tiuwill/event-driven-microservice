package com.cardservice.commons.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionRewardEventBuilder {
    private String transactionId;
    private String clientId;
    private String cardId;
    private BigDecimal amount;
    private Integer pointsEarned;
    private LocalDateTime timestamp;
    private boolean rollback;

    public TransactionRewardEventBuilder withTransactionId(String transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public TransactionRewardEventBuilder withClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public TransactionRewardEventBuilder withCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public TransactionRewardEventBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public TransactionRewardEventBuilder withPointsEarned(Integer pointsEarned) {
        this.pointsEarned = pointsEarned;
        return this;
    }

    public TransactionRewardEventBuilder withTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public TransactionRewardEventBuilder withRollback(boolean rollback) {
        this.rollback = rollback;
        return this;
    }

    public TransactionRewardEvent build() {
        return new TransactionRewardEvent(
                transactionId,
                clientId,
                cardId,
                amount,
                pointsEarned,
                timestamp,
                rollback
        );
    }
}
