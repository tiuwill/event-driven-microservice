package com.rewards.command.command.repository;

import com.rewards.command.command.model.RewardPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RewardPointsRepository extends JpaRepository<RewardPoints, Long> {
    Optional<RewardPoints> findByClientIdAndCardId(UUID clientId, UUID cardId);
}
