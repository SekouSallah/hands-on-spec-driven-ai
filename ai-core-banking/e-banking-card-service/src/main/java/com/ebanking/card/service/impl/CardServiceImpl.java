package com.ebanking.card.service.impl;

import com.ebanking.card.dto.request.CreateCardRequest;
import com.ebanking.card.dto.response.CardResponse;
import com.ebanking.card.entity.Card;
import com.ebanking.card.enums.CardStatus;
import com.ebanking.card.enums.CardType;
import com.ebanking.card.exception.BusinessRuleException;
import com.ebanking.card.exception.ResourceNotFoundException;
import com.ebanking.card.mapper.CardMapper;
import com.ebanking.card.repository.CardRepository;
import com.ebanking.card.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final CardMapper cardMapper;
    private static final Random RANDOM = new Random();

    @Override
    @Transactional
    public CardResponse createCard(CreateCardRequest request) {
        log.info("Creating card for account: {}", request.getAccountId());

        CardType type = CardType.DEBIT;
        if (request.getCardType() != null) {
            try { type = CardType.valueOf(request.getCardType().toUpperCase()); }
            catch (IllegalArgumentException e) { type = CardType.DEBIT; }
        }

        String pan = generatePan();
        String rawCvv = generateCvv();
        String hashedCvv = new BCryptPasswordEncoder().encode(rawCvv);

        Card card = Card.builder()
                .pan(pan)
                .expiryDate(LocalDate.now().plusYears(3))
                .cvv(hashedCvv)
                .status(CardStatus.ACTIVE)
                .cardType(type)
                .accountId(request.getAccountId())
                .build();

        Card saved = cardRepository.save(card);
        log.info("Card created: **** {}", pan.substring(12));
        return cardMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public CardResponse opposeCard(Long id) {
        Card card = findCardById(id);
        if (card.getStatus() == CardStatus.OPPOSITION) {
            throw new BusinessRuleException("CARD_IN_OPPOSITION", "Card is already in opposition");
        }
        card.setStatus(CardStatus.OPPOSITION);
        log.info("Card {} set to OPPOSITION (irreversible)", id);
        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse blockCard(Long id) {
        Card card = findCardById(id);
        if (card.getStatus() == CardStatus.BLOCKED) {
            throw new BusinessRuleException("CARD_ALREADY_BLOCKED", "Card is already blocked");
        }
        if (card.getStatus() == CardStatus.OPPOSITION) {
            throw new BusinessRuleException("CARD_IN_OPPOSITION", "Cannot block a card in opposition");
        }
        card.setStatus(CardStatus.BLOCKED);
        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Override
    @Transactional
    public CardResponse activateCard(Long id) {
        Card card = findCardById(id);
        if (card.getStatus() == CardStatus.OPPOSITION) {
            throw new BusinessRuleException("CARD_IN_OPPOSITION", "Cannot reactivate a card in opposition");
        }
        if (card.getStatus() == CardStatus.ACTIVE) {
            throw new BusinessRuleException("CARD_ALREADY_ACTIVE", "Card is already active");
        }
        card.setStatus(CardStatus.ACTIVE);
        return cardMapper.toResponse(cardRepository.save(card));
    }

    @Override
    @Transactional(readOnly = true)
    public CardResponse findById(Long id) {
        return cardMapper.toResponse(findCardById(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CardResponse> findByAccountId(Long accountId) {
        return cardRepository.findByAccountId(accountId).stream()
                .map(cardMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CardResponse> findAll(Pageable pageable) {
        return cardRepository.findAll(pageable).map(cardMapper::toResponse);
    }

    private String generatePan() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) sb.append(RANDOM.nextInt(10));
        return sb.toString();
    }

    private String generateCvv() {
        return String.format("%03d", RANDOM.nextInt(1000));
    }

    private Card findCardById(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "id", id));
    }
}
