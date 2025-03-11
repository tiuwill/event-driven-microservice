package com.cardservice.command.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DisputeRequest {
    @NotNull
    private String transactionId;

    @NotBlank
    private String reason;
}