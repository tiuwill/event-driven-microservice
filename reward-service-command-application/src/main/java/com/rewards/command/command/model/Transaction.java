package com.rewards.command.command.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @Column(name = "transaction_id")
    private UUID transactionId;

    @Column(nullable = false)
    private UUID clientId;

    @Column(nullable = false)
    private UUID cardId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer pointsEarned;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private boolean rollback = false;

    public Transaction(UUID transactionId, UUID clientId, UUID cardId, BigDecimal amount, Integer pointsEarned) {
        this.transactionId = transactionId;
        this.clientId = clientId;
        this.cardId = cardId;
        this.amount = amount;
        this.pointsEarned = pointsEarned;
        this.timestamp = LocalDateTime.now();
    }
}