package com.cardservice.query.repository;

import com.cardservice.query.dto.CardDetailViewData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardDetailViewRepository extends MongoRepository<CardDetailViewData, String> {
    Optional<CardDetailViewData> findByCardIdAndClientId(String cardId, String clientId);
}