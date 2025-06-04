package com.cardservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reward {
    @Id
    private String id;
    private String cardId;
    private String clientId;
    private BigDecimal totalPoints;
    private LocalDateTime lastUpdateDate;
}