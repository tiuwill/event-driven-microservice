package com.cardservice.query.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RewardRefundEvent {
    private String transactionId;
    private Integer amount;
    private String cardId;
    private LocalDateTime rollbackTime;
}
