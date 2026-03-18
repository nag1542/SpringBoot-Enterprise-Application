package com.enterprise.employee.application.dto;

import com.enterprise.employee.domain.enums.EmployeeStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record EmployeeRequestDTO(
        @NotBlank(message = "First name is required") String firstName,
        @NotBlank(message = "Last name is required") String lastName,
        @NotBlank(message = "Email is required") @Email(message = "Email should be valid") String email,
        @NotBlank(message = "Department is required") String department,
        @NotNull(message = "Salary is required") @Positive(message = "Salary must be positive") BigDecimal salary,
        @NotNull(message = "Status is required") EmployeeStatus status
) {
}
