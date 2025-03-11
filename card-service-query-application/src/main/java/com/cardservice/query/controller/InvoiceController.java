package com.cardservice.query.controller;


import com.cardservice.query.dto.InvoiceViewData;
import com.cardservice.query.repository.InvoiceViewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceViewRepository invoiceViewRepository;

    @GetMapping
    public ResponseEntity<InvoiceViewData> getInvoice(
            @RequestParam String cardId,
            @RequestParam String clientId) {

        return invoiceViewRepository.findByCardIdAndClientId(cardId, clientId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}