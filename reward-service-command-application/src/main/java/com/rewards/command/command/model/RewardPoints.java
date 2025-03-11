package com.rewards.command.command.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "reward_points")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardPoints {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID clientId;

    @Column(nullable = false, unique = true)
    private UUID cardId;

    @Column(nullable = false)
    private Integer totalPoints;

    // Constructor for creating new reward points
    public RewardPoints(UUID clientId, UUID cardId) {
        this.clientId = clientId;
        this.cardId = cardId;
        this.totalPoints = 0;
    }

    // Method to add points
    public void addPoints(Integer points) {
        this.totalPoints += points;
    }

    // Method to subtract points
    public void subtractPoints(Integer points) {
        this.totalPoints -= points;
        if (this.totalPoints < 0) {
            this.totalPoints = 0;
        }
    }
}