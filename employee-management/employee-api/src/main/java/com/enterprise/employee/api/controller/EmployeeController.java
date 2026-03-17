package com.enterprise.employee.api.controller;

import com.enterprise.common.constants.AppConstants;
import com.enterprise.common.model.ApiResponse;
import com.enterprise.employee.application.dto.EmployeeRequestDTO;
import com.enterprise.employee.application.dto.EmployeeResponseDTO;
import com.enterprise.employee.application.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> createEmployee(@Valid @RequestBody EmployeeRequestDTO request) {
        EmployeeResponseDTO response = employeeService.createEmployee(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED.value(), "Employee created", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> getEmployee(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), AppConstants.DEFAULT_SUCCESS_MESSAGE, employeeService.getEmployee(id)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<EmployeeResponseDTO>>> searchEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String name,
            Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), AppConstants.DEFAULT_SUCCESS_MESSAGE,
                employeeService.searchEmployees(department, name, pageable)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<EmployeeResponseDTO>> updateEmployee(@PathVariable UUID id,
                                                                           @Valid @RequestBody EmployeeRequestDTO request) {
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Employee updated",
                employeeService.updateEmployee(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok(ApiResponse.success(HttpStatus.OK.value(), "Employee deleted", null));
    }
}
