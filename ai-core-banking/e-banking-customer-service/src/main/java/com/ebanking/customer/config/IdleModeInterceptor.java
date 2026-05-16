package com.ebanking.customer.config;

import com.ebanking.customer.exception.IdleModeException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

/**
 * HTTP interceptor that enforces Idle Mode.
 * When idle mode is enabled, all write operations (POST, PUT, PATCH, DELETE)
 * are blocked with a 503 response. Read operations (GET) remain available.
 */
@Component
@Slf4j
public class IdleModeInterceptor implements HandlerInterceptor {

    private static final Set<String> WRITE_METHODS = Set.of("POST", "PUT", "PATCH", "DELETE");

    @Value("${ebanking.idle.enabled:false}")
    private boolean idleEnabled;

    /**
     * Programmatically enable or disable idle mode.
     */
    public void setIdleEnabled(boolean enabled) {
        this.idleEnabled = enabled;
        log.info("Idle mode {}", enabled ? "ACTIVATED" : "DEACTIVATED");
    }

    public boolean isIdleEnabled() {
        return idleEnabled;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (idleEnabled && WRITE_METHODS.contains(request.getMethod().toUpperCase())) {
            // Allow idle mode toggle endpoint even in idle mode
            if (request.getRequestURI().contains("/api/system/idle")) {
                return true;
            }

            log.warn("Idle mode active — blocked {} {}", request.getMethod(), request.getRequestURI());
            throw new IdleModeException();
        }
        return true;
    }
}
