package com.rewardservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RewardTransaction {
    @Id
    private String transactionId;
    private String cardId;
    private String customerId;
    private BigDecimal amount;
    private Integer rewardPoints;
    private LocalDateTime transactionDate;
    private boolean rolledBack;
}