package com.rewardservice.query.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "card_rewards")
public class CardRewards {
    @Id
    private String cardId;
    private String customerId;
    @Builder.Default
    private List<RewardTransaction> transactions = new ArrayList<>();
}
