package com.enterprise.common.model;

import lombok.Builder;

import java.time.Instant;

@Builder
public record ApiResponse<T>(Instant timestamp, int status, String message, T data) {

    public static <T> ApiResponse<T> success(int status, String message, T data) {
        return ApiResponse.<T>builder()
                .timestamp(Instant.now())
                .status(status)
                .message(message)
                .data(data)
                .build();
    }
}
