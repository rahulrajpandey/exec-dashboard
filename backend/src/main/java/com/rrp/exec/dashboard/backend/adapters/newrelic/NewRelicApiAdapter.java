package com.rrp.exec.dashboard.backend.adapters.newrelic;

import com.rrp.exec.dashboard.backend.models.dto.TopicHistoryDTO;

public class NewRelicApiAdapter implements NewRelicMetricsPort {

    @Override
    public TopicHistoryDTO getTopicHistory(String topic, int days) {
        // TODO: call NRQL API, map to TopicHistoryDTO
        throw new UnsupportedOperationException("REAL NewRelic adapter not implemented yet");
    }
}