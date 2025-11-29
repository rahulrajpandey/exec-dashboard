package com.rrp.exec.dashboard.backend.controllers;

import com.rrp.exec.dashboard.backend.models.dto.TopicHistoryDTO;
import com.rrp.exec.dashboard.backend.services.historical.HistoricalMetricService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Mono;

@Controller
public class HistoricalGraphQLController {

    private final HistoricalMetricService historicalMetricService;

    public HistoricalGraphQLController(HistoricalMetricService historicalMetricService) {
        this.historicalMetricService = historicalMetricService;
    }

    @QueryMapping
    public Mono<TopicHistoryDTO> topicHistory(@Argument String topic, @Argument int days) {
        return historicalMetricService.getTopicHistory(topic, days);
    }
}