package com.rrp.exec.dashboard.backend.adapters.kafka;

import com.rrp.exec.dashboard.backend.models.dto.ClusterMetricsDTO;

public class KafkaAdminClientAdapter implements KafkaMetricsPort {

    // TODO inject AdminClient via constructor, etc.

    @Override
    public ClusterMetricsDTO getClusterSnapshot() {
        // TODO: implement using AdminClient (listTopics, describeTopics, offsets, etc.)
        throw new UnsupportedOperationException("REAL Kafka adapter not implemented yet");
    }
}