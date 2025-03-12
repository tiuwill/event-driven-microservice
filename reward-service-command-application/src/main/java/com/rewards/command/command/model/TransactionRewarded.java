package com.rewards.command.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRewarded {
    private String transactionId;
    private Integer amount;
    private LocalDateTime rewardedTime;


    public static TransactionRewarded fromRewardedTransaction(Transaction transaction) {
        return new TransactionRewarded(transaction.getTransactionId().toString(), transaction.getAmount().intValue(), transaction.getTimestamp());
    }

}