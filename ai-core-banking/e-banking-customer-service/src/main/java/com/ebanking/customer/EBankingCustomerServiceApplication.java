package com.ebanking.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the E-Banking Customer Service.
 * Handles customer management, KYC, and customer lifecycle operations.
 */
@SpringBootApplication
public class EBankingCustomerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EBankingCustomerServiceApplication.class, args);
    }
}
