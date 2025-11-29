package com.rrp.exec.dashboard.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
public class ModeConfig {
    /**
     * Allowed values: MOCK, REAL
     */
    private String mode = "MOCK";

    public String mode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isMock() {
        return "MOCK".equalsIgnoreCase(mode);
    }
}