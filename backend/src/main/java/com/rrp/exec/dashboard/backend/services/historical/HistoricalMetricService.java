package com.rrp.exec.dashboard.backend.services.historical;

import com.rrp.exec.dashboard.backend.adapters.newrelic.NewRelicMetricsPort;
import com.rrp.exec.dashboard.backend.models.dto.TopicHistoryDTO;
import java.util.concurrent.ExecutorService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class HistoricalMetricService {

  private final NewRelicMetricsPort newRelicMetricsPort;
  private final ExecutorService virtualThreadExecutor;

  public HistoricalMetricService(
      NewRelicMetricsPort newRelicMetricsPort, ExecutorService virtualThreadExecutor) {
    this.newRelicMetricsPort = newRelicMetricsPort;
    this.virtualThreadExecutor = virtualThreadExecutor;
  }

  public Mono<TopicHistoryDTO> getTopicHistory(String topic, int days) {
    return Mono.fromCallable(() -> newRelicMetricsPort.getTopicHistory(topic, days))
        .subscribeOn(Schedulers.fromExecutor(virtualThreadExecutor));
  }
}
