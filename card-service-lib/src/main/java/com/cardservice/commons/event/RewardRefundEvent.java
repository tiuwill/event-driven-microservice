package com.cardservice.commons.event;

import java.time.LocalDateTime;

public class RewardRefundEvent {
    private String transactionId;
    private Integer amount;
    private String cardId;
    private LocalDateTime rollbackTime;

    public RewardRefundEvent() {
    }

    public RewardRefundEvent(String transactionId, Integer amount, String cardId, LocalDateTime rollbackTime) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.cardId = cardId;
        this.rollbackTime = rollbackTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public LocalDateTime getRollbackTime() {
        return rollbackTime;
    }

    public void setRollbackTime(LocalDateTime rollbackTime) {
        this.rollbackTime = rollbackTime;
    }
}
