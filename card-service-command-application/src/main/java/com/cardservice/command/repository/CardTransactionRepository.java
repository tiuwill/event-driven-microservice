package com.cardservice.command.repository;


import com.cardservice.command.model.CardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardTransactionRepository extends JpaRepository<CardTransaction, UUID> {
}
