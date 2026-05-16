package com.ebanking.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Response DTO for customer statistics (reporting endpoint).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerStatsResponse {

    private LocalDateTime timestamp;
    private long totalCustomers;
    private long activeCustomers;
    private long inactiveCustomers;
    private long suspendedCustomers;
    private Map<String, Object> additionalData;
}
