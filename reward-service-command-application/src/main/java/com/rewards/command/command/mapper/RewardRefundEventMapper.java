package com.rewards.command.command.mapper;

import com.cardservice.commons.event.RewardRefundEvent;
import com.rewards.command.command.model.Transaction;

public class RewardRefundEventMapper {

    public static RewardRefundEvent fromRewardedTransaction(Transaction transaction) {
        return new RewardRefundEvent(transaction.getTransactionId().toString(), transaction.getAmount().intValue(), transaction.getCardId().toString(), transaction.getTimestamp());
    }
}
