package com.rrp.exec.dashboard.backend.config;

import com.rrp.exec.dashboard.backend.adapters.couchbase.CouchbaseMetadataAdapter;
import com.rrp.exec.dashboard.backend.adapters.couchbase.MetadataPort;
import com.rrp.exec.dashboard.backend.adapters.couchbase.MockMetadataAdapter;
import com.rrp.exec.dashboard.backend.adapters.kafka.KafkaAdminClientAdapter;
import com.rrp.exec.dashboard.backend.adapters.kafka.KafkaMetricsPort;
import com.rrp.exec.dashboard.backend.adapters.kafka.MockKafkaMetricsAdapter;
import com.rrp.exec.dashboard.backend.adapters.newrelic.MockNewRelicMetricsAdapter;
import com.rrp.exec.dashboard.backend.adapters.newrelic.NewRelicApiAdapter;
import com.rrp.exec.dashboard.backend.adapters.newrelic.NewRelicMetricsPort;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

  @Bean
  public ExecutorService virtualThreadExecutor() {
    return Executors.newVirtualThreadPerTaskExecutor();
  }

  @Bean
  public KafkaMetricsPort kafkaMetricsPort(ModeConfig config) {
    return config.isMock()
        ? new MockKafkaMetricsAdapter()
        : new KafkaAdminClientAdapter(); // real implementation later
  }

  @Bean
  public NewRelicMetricsPort newRelicMetricsPort(ModeConfig config) {
    return config.isMock()
        ? new MockNewRelicMetricsAdapter()
        : new NewRelicApiAdapter(); // real implementation later
  }

  @Bean
  public MetadataPort metadataPort(ModeConfig config) {
    return config.isMock()
        ? new MockMetadataAdapter()
        : new CouchbaseMetadataAdapter(); // real implementation later
  }
}
