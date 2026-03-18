package com.enterprise.employee.application.dto;

import com.enterprise.employee.domain.enums.EmployeeStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record EmployeeResponseDTO(
        UUID id,
        String firstName,
        String lastName,
        String email,
        String department,
        BigDecimal salary,
        EmployeeStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}
