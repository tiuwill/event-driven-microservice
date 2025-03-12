package com.rewards.command.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionRefundEvent {

    private String transactionId;
    private String cardId;

    public static TransactionRefundEvent fromRewardedTransaction(Transaction transaction) {
        return new TransactionRefundEvent(transaction.getTransactionId().toString(), transaction.getCardId().toString());
    }
}
