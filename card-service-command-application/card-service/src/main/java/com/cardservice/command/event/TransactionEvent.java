package com.cardservice.command.event;

import com.cardservice.command.model.CardTransaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    public static TransactionEvent fromCardTransaction(CardTransaction cardTransaction) {
        return new TransactionEvent(
                cardTransaction.getId().toString(),
                cardTransaction.getCardId().toString(),
                cardTransaction.getCustomerId().toString(),
                cardTransaction.getAmount(),
                cardTransaction.getCnpj(),
                cardTransaction.getStatus().name(),
                cardTransaction.getTransactionDate(),
                cardTransaction.getDescription()
        );
    }
}