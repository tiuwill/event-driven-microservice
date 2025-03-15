package com.cardservice.commons.event;

public class RefundEvent {

    private String transactionId;
    private String cardId;

    public RefundEvent() {
    }

    public RefundEvent(String transactionId, String cardId) {
        this.transactionId = transactionId;
        this.cardId = cardId;
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
}
