package com.ebanking.card.service;

import com.ebanking.card.dto.request.CreateCardRequest;
import com.ebanking.card.dto.response.CardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface CardService {
    CardResponse createCard(CreateCardRequest request);
    CardResponse opposeCard(Long id);
    CardResponse blockCard(Long id);
    CardResponse activateCard(Long id);
    CardResponse findById(Long id);
    List<CardResponse> findByAccountId(Long accountId);
    Page<CardResponse> findAll(Pageable pageable);
}
