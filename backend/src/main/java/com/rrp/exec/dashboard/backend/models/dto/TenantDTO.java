package com.rrp.exec.dashboard.backend.models.dto;

import java.util.List;

public record TenantDTO(
        String tenantId,
        String tenantName,
        List<String> topics,
        String slaTier
) {}