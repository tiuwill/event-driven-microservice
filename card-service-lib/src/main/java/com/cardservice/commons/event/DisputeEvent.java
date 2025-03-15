package com.cardservice.commons.event;


public class DisputeEvent {

    private String cardId;
    private String transactionId;
    private String reason;

    public DisputeEvent() {
    }

    public DisputeEvent(String cardId, String transactionId, String reason) {
        this.cardId = cardId;
        this.transactionId = transactionId;
        this.reason = reason;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
