package com.rrp.exec.dashboard.backend.adapters.kafka;

import com.rrp.exec.dashboard.backend.models.dto.ClusterMetricsDTO;

public interface KafkaMetricsPort {
    ClusterMetricsDTO getClusterSnapshot();
}
