package com.enterprise.employee.application.service;

import com.enterprise.employee.application.dto.EmployeeRequestDTO;
import com.enterprise.employee.application.dto.EmployeeResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EmployeeService {
    EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO);

    EmployeeResponseDTO updateEmployee(UUID id, EmployeeRequestDTO requestDTO);

    EmployeeResponseDTO getEmployee(UUID id);

    Page<EmployeeResponseDTO> searchEmployees(String department, String name, Pageable pageable);

    void deleteEmployee(UUID id);
}
