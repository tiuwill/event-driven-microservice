package com.cardservice.query.repository;

import com.cardservice.query.dto.InvoiceViewData;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvoiceViewRepository extends MongoRepository<InvoiceViewData, String> {
    Optional<InvoiceViewData> findByCardId(String cardId);
}