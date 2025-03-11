package com.cardservice.command.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card_transactions")
public class CardTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String cnpj;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, name = "customer_id")
    private UUID customerId;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(nullable = false, name = "card_id")
    private UUID cardId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(name = "dispute_reason")
    private String disputeReason;

    @PrePersist
    public void updateTransactionDate() {
        if (transactionDate == null) {
            transactionDate = LocalDateTime.now();
        }
    }

}
