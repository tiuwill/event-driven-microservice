package com.cardservice.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "invoice_view")
public class InvoiceViewData {
    @Id
    private String id;
    private String cardId;
    private String clientId;
    private BigDecimal totalAmount;
    private Integer totalRewardPoints;
    private LocalDateTime lastUpdateDate;
    private List<TransactionDetail> transactions;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TransactionDetail {
        private String id;
        private BigDecimal amount;
        private String description;
        private LocalDateTime transactionDate;
        private String status; // For disputes: PENDING, APPROVED, REJECTED
    }
}

