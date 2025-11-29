package com.rrp.exec.dashboard.backend.models.dto;

import java.util.List;

public record TopicHistoryDTO(
        String topic,
        List<DailyCountDTO> dailyCounts
) {}