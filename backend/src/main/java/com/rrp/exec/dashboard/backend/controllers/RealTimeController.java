package com.rrp.exec.dashboard.backend.controllers;

import com.rrp.exec.dashboard.backend.models.dto.ApiResponse;
import com.rrp.exec.dashboard.backend.models.dto.ClusterMetricsDTO;
import com.rrp.exec.dashboard.backend.services.realtime.RealTimeMetricService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/realtime")
public class RealTimeController {

    private final RealTimeMetricService realTimeMetricService;

    public RealTimeController(RealTimeMetricService realTimeMetricService) {
        this.realTimeMetricService = realTimeMetricService;
    }

    @GetMapping("/cluster")
    public Mono<ApiResponse<ClusterMetricsDTO>> getClusterMetrics() {
        return realTimeMetricService.getClusterSnapshot()
                .map(ApiResponse::ok);
    }
}
