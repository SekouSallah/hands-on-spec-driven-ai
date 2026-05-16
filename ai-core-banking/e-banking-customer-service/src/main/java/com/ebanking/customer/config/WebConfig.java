package com.ebanking.customer.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC configuration to register the Idle Mode interceptor.
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final IdleModeInterceptor idleModeInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(idleModeInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns(
                        "/api-docs/**",
                        "/swagger-ui/**",
                        "/actuator/**"
                );
    }
}
