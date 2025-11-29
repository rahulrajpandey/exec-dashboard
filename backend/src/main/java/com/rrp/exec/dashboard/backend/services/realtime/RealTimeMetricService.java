package com.rrp.exec.dashboard.backend.services.realtime;

import com.rrp.exec.dashboard.backend.adapters.kafka.KafkaMetricsPort;
import com.rrp.exec.dashboard.backend.models.dto.ClusterMetricsDTO;
import java.util.concurrent.ExecutorService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
public class RealTimeMetricService {

  private final KafkaMetricsPort kafkaMetricsPort;
  private final ExecutorService virtualThreadExecutor;

  public RealTimeMetricService(
      KafkaMetricsPort kafkaMetricsPort, ExecutorService virtualThreadExecutor) {
    this.kafkaMetricsPort = kafkaMetricsPort;
    this.virtualThreadExecutor = virtualThreadExecutor;
  }

  public Mono<ClusterMetricsDTO> getClusterSnapshot() {
    return Mono.fromCallable(kafkaMetricsPort::getClusterSnapshot)
        .subscribeOn(Schedulers.fromExecutor(virtualThreadExecutor));
  }
}
