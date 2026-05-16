package com.ebanking.customer.controller;

import com.ebanking.customer.config.IdleModeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * System controller for managing idle mode and system-level operations.
 */
@RestController
@RequestMapping("/api/system")
@RequiredArgsConstructor
@Tag(name = "System", description = "System management operations")
public class SystemController {

    private final IdleModeInterceptor idleModeInterceptor;

    @GetMapping("/idle")
    @Operation(summary = "Get current idle mode status")
    public ResponseEntity<Map<String, Object>> getIdleStatus() {
        return ResponseEntity.ok(Map.of(
                "idleMode", idleModeInterceptor.isIdleEnabled()
        ));
    }

    @PatchMapping("/idle")
    @Operation(summary = "Enable or disable idle mode (ADMIN only)")
    public ResponseEntity<Map<String, Object>> setIdleMode(@RequestParam boolean enabled) {
        idleModeInterceptor.setIdleEnabled(enabled);
        return ResponseEntity.ok(Map.of(
                "idleMode", enabled,
                "activatedAt", LocalDateTime.now().toString(),
                "message", enabled
                        ? "Idle mode activated. Write operations are now blocked."
                        : "Idle mode deactivated. All operations are now available."
        ));
    }
}
