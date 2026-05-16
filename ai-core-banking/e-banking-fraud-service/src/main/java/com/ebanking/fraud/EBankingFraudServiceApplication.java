package com.ebanking.fraud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EBankingFraudServiceApplication {
    public static void main(String[] args) { SpringApplication.run(EBankingFraudServiceApplication.class, args); }
}
