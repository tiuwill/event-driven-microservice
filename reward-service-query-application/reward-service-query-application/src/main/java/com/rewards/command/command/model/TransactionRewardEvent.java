package com.rewards.command.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRewardEvent {
    private String transactionId;
    private String clientId;
    private String cardId;
    private BigDecimal amount;
    private Integer pointsEarned;
    private LocalDateTime timestamp;
    private boolean rollback;
}