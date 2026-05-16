package com.ebanking.fraud.mapper;

import com.ebanking.fraud.dto.response.FraudAlertResponse;
import com.ebanking.fraud.entity.FraudAlert;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FraudAlertMapper {
    FraudAlertResponse toResponse(FraudAlert fraudAlert);
}
