package com.cardservice.command.event;

import com.cardservice.command.model.CardTransaction;
import com.cardservice.command.model.TransactionStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEvent {
    @Id
    private String id;
    private String cardId;
    private String clientId;
    private BigDecimal amount;
    private String cnpj;
    private String status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime purchaseDate;
    private String description;

    public static TransactionEvent createTransactionCreatedEventFrom(CardTransaction cardTransaction) {
        return new TransactionEvent(
                UUID.randomUUID().toString(),
                cardTransaction.getCardId().toString(),
                cardTransaction.getCustomerId().toString(),
                cardTransaction.getAmount(),
                cardTransaction.getCnpj(),
                TransactionStatus.APPROVED.name(),
                cardTransaction.getTransactionDate(),
                cardTransaction.getDescription()
        );
    }


    public static TransactionEvent createTransactionDisputedEventFrom(CardTransaction cardTransaction) {
        return new TransactionEvent(
                cardTransaction.getId().toString(),
                cardTransaction.getCardId().toString(),
                cardTransaction.getCustomerId().toString(),
                cardTransaction.getAmount(),
                cardTransaction.getCnpj(),
                TransactionStatus.DISPUTED.name(),
                cardTransaction.getTransactionDate(),
                cardTransaction.getDescription()
        );
    }


}