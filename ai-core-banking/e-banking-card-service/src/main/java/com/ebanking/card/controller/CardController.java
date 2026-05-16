package com.ebanking.card.controller;

import com.ebanking.card.dto.request.CreateCardRequest;
import com.ebanking.card.dto.response.CardResponse;
import com.ebanking.card.service.CardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
@Tag(name = "Card Management", description = "Operations for managing bank cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    @Operation(summary = "Create a new card")
    public ResponseEntity<CardResponse> create(@Valid @RequestBody CreateCardRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cardService.createCard(request));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get card by ID")
    public ResponseEntity<CardResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.findById(id));
    }

    @GetMapping
    @Operation(summary = "Get all cards")
    public ResponseEntity<Page<CardResponse>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(cardService.findAll(pageable));
    }

    @GetMapping("/account/{accountId}")
    @Operation(summary = "Get cards by account")
    public ResponseEntity<List<CardResponse>> findByAccount(@PathVariable Long accountId) {
        return ResponseEntity.ok(cardService.findByAccountId(accountId));
    }

    @PatchMapping("/{id}/oppose")
    @Operation(summary = "Set card in opposition (irreversible)")
    public ResponseEntity<CardResponse> oppose(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.opposeCard(id));
    }

    @PatchMapping("/{id}/block")
    @Operation(summary = "Block a card (reversible)")
    public ResponseEntity<CardResponse> block(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.blockCard(id));
    }

    @PatchMapping("/{id}/activate")
    @Operation(summary = "Activate a blocked card")
    public ResponseEntity<CardResponse> activate(@PathVariable Long id) {
        return ResponseEntity.ok(cardService.activateCard(id));
    }
}
