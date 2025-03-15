package com.cardservice.query.controller;


import com.cardservice.commons.TestEvent;
import com.cardservice.query.dto.CardDetailViewData;
import com.cardservice.query.repository.CardDetailViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/card-details")
public class CardDetailsController {

    @Autowired
    private CardDetailViewRepository cardDetailViewRepository;



    @GetMapping
    public ResponseEntity<CardDetailViewData> getCardDetails(
            @RequestParam String cardId,
            @RequestParam String clientId) {

        return cardDetailViewRepository.findByCardIdAndClientId(cardId, clientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}