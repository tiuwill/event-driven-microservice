package com.rewards.command.command.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TransactionRewardRolledBack {
    private String transactionId;
    private Integer rolledBackAmount;
    private LocalDateTime rollbackTime;

    //TODO voltar pro reward service e continuar metodo de rollback
}
