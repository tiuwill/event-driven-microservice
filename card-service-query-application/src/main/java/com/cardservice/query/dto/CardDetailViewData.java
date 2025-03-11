package com.cardservice.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "card_details_view")
public class CardDetailViewData {
    @Id
    private String id;
    private String cardId;
    private String clientId;
    private String cardNumber;
    private String cardholderName;
    private LocalDate expirationDate;
    private BigDecimal creditLimit;
    private BigDecimal availableCredit;
    private List<PurchaseDetail> purchases;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PurchaseDetail {
        private String id;
        private BigDecimal amount;
        private String merchant;
        private LocalDateTime purchaseDate;
        private String description;
    }
}
