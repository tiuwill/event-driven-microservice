package com.cardservice.command.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefundRequest {
    @NotNull
    private String transactionId;
}
