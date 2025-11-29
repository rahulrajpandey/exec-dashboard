package com.rrp.exec.dashboard.backend.models.dto;

public record TopicMetricsDTO(
        String topic,
        double messagesPerSecond,
        long consumerLag,
        long sizeBytes,
        int partitionCount,
        int underReplicatedPartitions,
        int inSyncReplicas
) {}