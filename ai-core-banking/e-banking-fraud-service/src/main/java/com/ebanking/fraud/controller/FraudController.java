package com.ebanking.fraud.controller;

import com.ebanking.fraud.dto.response.FraudAlertResponse;
import com.ebanking.fraud.enums.FraudLevel;
import com.ebanking.fraud.service.FraudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud")
@RequiredArgsConstructor
@Tag(name = "Fraud Management", description = "Operations for fraud detection and alerts")
public class FraudController {

    private final FraudService fraudService;

    @GetMapping
    @Operation(summary = "Get all fraud alerts")
    public ResponseEntity<Page<FraudAlertResponse>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(fraudService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get fraud alert by ID")
    public ResponseEntity<FraudAlertResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(fraudService.findById(id));
    }

    @GetMapping("/level/{level}")
    @Operation(summary = "Get alerts by level")
    public ResponseEntity<Page<FraudAlertResponse>> findByLevel(
            @PathVariable FraudLevel level, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(fraudService.findByLevel(level, pageable));
    }

    @GetMapping("/status")
    @Operation(summary = "Get alerts by resolution status")
    public ResponseEntity<Page<FraudAlertResponse>> findByResolved(
            @RequestParam Boolean resolved, @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(fraudService.findByResolved(resolved, pageable));
    }

    @PatchMapping("/{id}/resolve")
    @Operation(summary = "Mark an alert as resolved")
    public ResponseEntity<FraudAlertResponse> resolve(@PathVariable Long id) {
        return ResponseEntity.ok(fraudService.resolveAlert(id));
    }

    @PostMapping("/analyze")
    @Operation(summary = "Manually trigger batch fraud analysis")
    public ResponseEntity<String> triggerAnalysis() {
        fraudService.analyzeRecentTransactions();
        return ResponseEntity.ok("Fraud analysis triggered successfully");
    }
}
