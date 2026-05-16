package com.ebanking.card.repository;

import com.ebanking.card.entity.Card;
import com.ebanking.card.enums.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByAccountId(Long accountId);
    Page<Card> findByAccountId(Long accountId, Pageable pageable);
    Page<Card> findByStatus(CardStatus status, Pageable pageable);
    long countByStatus(CardStatus status);
}
