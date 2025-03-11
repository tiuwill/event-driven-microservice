package com.rewardservice.query.controller;

import com.rewardservice.query.model.CardRewards;
import com.rewardservice.query.service.RewardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rewards")
@RequiredArgsConstructor
public class RewardsController {

    private final RewardsService rewardsService;

    @GetMapping("/card/{cardId}")
    public ResponseEntity<CardRewards> getCardRewards(@PathVariable String cardId) {
        return ResponseEntity.ok(rewardsService.getCardRewards(cardId));
    }
}

