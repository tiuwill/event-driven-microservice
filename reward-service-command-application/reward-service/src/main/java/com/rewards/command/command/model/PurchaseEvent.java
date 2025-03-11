package com.rewards.command.command.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseEvent {
    private UUID cardId;
    private UUID clientId;
    private BigDecimal amount;
    private UUID transactionId;
}