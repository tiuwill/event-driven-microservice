package com.rewardservice.query.repository;

import com.rewardservice.query.model.CardRewards;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CardRewardsRepository extends MongoRepository<CardRewards, String> {

    //@Query("{ 'transactions': { $elemMatch: { 'rolledBack': true } }, 'transactions.rolledBack': true }")
    List<CardRewards> findByTransactionsRolledBack(boolean rolledBack);
}
