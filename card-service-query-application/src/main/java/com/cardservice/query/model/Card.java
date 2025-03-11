package com.cardservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cards")
public class Card {
    @Id
    private String id;
    private String cardNumber;
    private String clientId;
    private LocalDate expirationDate;
    private String cardholderName;
    private BigDecimal creditLimit;
    private BigDecimal availableCredit;
}