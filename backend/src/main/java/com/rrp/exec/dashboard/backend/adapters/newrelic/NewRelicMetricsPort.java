package com.rrp.exec.dashboard.backend.adapters.newrelic;

import com.rrp.exec.dashboard.backend.models.dto.TopicHistoryDTO;

public interface NewRelicMetricsPort {
    TopicHistoryDTO getTopicHistory(String topic, int days);
}
