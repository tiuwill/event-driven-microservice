package com.cardservice.command.service;

import com.cardservice.command.model.CardLimit;
import com.cardservice.command.repository.CardLimitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardLimitService {

    private final CardLimitRepository cardLimitRepository;

    @Transactional
    public CardLimit deductLimit(UUID cardId, BigDecimal amount) {
        log.info("Deducting amount {} from card {}", amount, cardId);

        CardLimit cardLimit = cardLimitRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card limit not found for card ID: " + cardId));

        cardLimit.setAvailableLimit(cardLimit.getAvailableLimit().subtract(amount));
        cardLimit.setLastUpdated(LocalDateTime.now());

        return cardLimitRepository.save(cardLimit);
    }

    @Transactional
    public CardLimit increaseLimit(UUID cardId, BigDecimal amount) {
        log.info("Increasing limit by amount {} for card {}", amount, cardId);

        CardLimit cardLimit = cardLimitRepository.findById(cardId)
                .orElseThrow(() -> new RuntimeException("Card limit not found for card ID: " + cardId));

        cardLimit.setAvailableLimit(cardLimit.getAvailableLimit().add(amount));
        cardLimit.setLastUpdated(LocalDateTime.now());

        return cardLimitRepository.save(cardLimit);
    }

}