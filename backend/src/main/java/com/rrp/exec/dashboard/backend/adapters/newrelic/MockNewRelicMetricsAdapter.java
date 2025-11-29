package com.rrp.exec.dashboard.backend.adapters.newrelic;

import com.rrp.exec.dashboard.backend.models.dto.TopicHistoryDTO;
import com.rrp.exec.dashboard.backend.util.JsonLoader;

public class MockNewRelicMetricsAdapter implements NewRelicMetricsPort {

  @Override
  public TopicHistoryDTO getTopicHistory(String topic, int days) {
    return JsonLoader.load("mock-data/topic-history.json", TopicHistoryDTO.class);
  }
}
