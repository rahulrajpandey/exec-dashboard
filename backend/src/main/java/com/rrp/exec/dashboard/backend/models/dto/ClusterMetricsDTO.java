package com.rrp.exec.dashboard.backend.models.dto;

import java.util.List;

public record ClusterMetricsDTO(
        String clusterId,
        long timestamp,
        int totalTopics,
        int totalPartitions,
        long totalLag,
        List<TopicMetricsDTO> topics
) {}
