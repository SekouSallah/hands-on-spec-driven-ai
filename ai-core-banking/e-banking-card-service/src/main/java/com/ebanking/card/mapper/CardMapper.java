package com.ebanking.card.mapper;

import com.ebanking.card.dto.response.CardResponse;
import com.ebanking.card.entity.Card;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(target = "maskedPan", source = "pan", qualifiedByName = "maskPan")
    CardResponse toResponse(Card card);

    @Named("maskPan")
    default String maskPan(String pan) {
        if (pan == null || pan.length() < 4) return "****";
        return "**** **** **** " + pan.substring(pan.length() - 4);
    }
}
