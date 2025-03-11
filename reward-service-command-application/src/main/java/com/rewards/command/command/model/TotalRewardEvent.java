package com.rewards.command.command.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TotalRewardEvent {
    private UUID clientId;
    private UUID cardId;
    private Integer totalPoints;
    private LocalDateTime timestamp;
}