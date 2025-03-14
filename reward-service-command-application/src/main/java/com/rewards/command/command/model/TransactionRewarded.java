package com.rewards.command.command.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String cardId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime rewardedTime;


    public static TransactionRewarded fromRewardedTransaction(Transaction transaction) {
        return new TransactionRewarded(transaction.getTransactionId().toString(), transaction.getAmount().intValue(), transaction.getCardId().toString(), transaction.getTimestamp());
    }

}