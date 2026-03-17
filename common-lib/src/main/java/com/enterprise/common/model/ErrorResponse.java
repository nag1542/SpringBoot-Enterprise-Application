package com.enterprise.common.model;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ErrorResponse(Instant timestamp, int status, String message, String path, List<String> errors) {
}
