package com.rewards.command.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RewardRefundEvent {
    private String transactionId;
    private Integer amount;
    private String cardId;
    private LocalDateTime rollbackTime;

    public static RewardRefundEvent fromRewardedTransaction(Transaction transaction) {
        return new RewardRefundEvent(transaction.getTransactionId().toString(), transaction.getAmount().intValue(), transaction.getCardId().toString(), transaction.getTimestamp());
    }
}
