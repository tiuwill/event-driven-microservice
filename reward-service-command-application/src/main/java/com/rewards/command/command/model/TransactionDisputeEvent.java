package com.rewards.command.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TransactionDisputeEvent {

    private String transactionId;
    private String cardId;
    private String reason;

}
