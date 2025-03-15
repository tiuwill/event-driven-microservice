package com.cardservice.commons.event;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;


public class TransactionRewardEvent {
    private String transactionId;
    private String cardId;
    private Integer amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime rewardedTime;

    public TransactionRewardEvent() {
    }

    public TransactionRewardEvent(String transactionId, String cardId, Integer amount, LocalDateTime rewardedTime) {
        this.transactionId = transactionId;
        this.cardId = cardId;
        this.amount = amount;
        this.rewardedTime = rewardedTime;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public LocalDateTime getRewardedTime() {
        return rewardedTime;
    }

    public void setRewardedTime(LocalDateTime rewardedTime) {
        this.rewardedTime = rewardedTime;
    }
}