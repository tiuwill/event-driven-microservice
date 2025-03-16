package com.cardservice.commons.event;


import java.math.BigDecimal;
import java.time.LocalDateTime;


public class TransactionRewardEvent {
    private String transactionId;
    private String clientId;
    private String cardId;
    private BigDecimal amount;
    private Integer pointsEarned;
    private LocalDateTime timestamp;
    private boolean rollback;

    public TransactionRewardEvent() {
    }

    public TransactionRewardEvent(String transactionId, String clientId, String cardId, BigDecimal amount, Integer pointsEarned, LocalDateTime timestamp, boolean rollback) {
        this.transactionId = transactionId;
        this.clientId = clientId;
        this.cardId = cardId;
        this.amount = amount;
        this.pointsEarned = pointsEarned;
        this.timestamp = timestamp;
        this.rollback = rollback;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getPointsEarned() {
        return pointsEarned;
    }

    public void setPointsEarned(Integer pointsEarned) {
        this.pointsEarned = pointsEarned;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRollback() {
        return rollback;
    }

    public void setRollback(boolean rollback) {
        this.rollback = rollback;
    }
}