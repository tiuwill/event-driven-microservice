package com.rewardservice.query.model.event;

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
    private String cardId;
    private String customerId;
    private BigDecimal amount;
    private BigDecimal rewardPoints;
    private LocalDateTime transactionDate;
    private String description;
}