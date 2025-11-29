package com.rrp.exec.dashboard.backend.models.dto;

public record ApiResponse<T>(
        T data,
        String status,
        long timestamp
) {
    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(data, "SUCCESS", System.currentTimeMillis());
    }
}