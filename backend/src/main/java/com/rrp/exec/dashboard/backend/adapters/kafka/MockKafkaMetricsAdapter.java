package com.rrp.exec.dashboard.backend.adapters.kafka;

import com.rrp.exec.dashboard.backend.models.dto.ClusterMetricsDTO;
import com.rrp.exec.dashboard.backend.util.JsonLoader;

public class MockKafkaMetricsAdapter implements KafkaMetricsPort {

    @Override
    public ClusterMetricsDTO getClusterSnapshot() {
        return JsonLoader.load("mock-data/cluster.json", ClusterMetricsDTO.class);
    }
}