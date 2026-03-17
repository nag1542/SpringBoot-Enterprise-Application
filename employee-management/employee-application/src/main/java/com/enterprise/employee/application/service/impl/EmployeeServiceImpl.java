package com.enterprise.employee.application.service.impl;

import com.enterprise.common.exception.BusinessException;
import com.enterprise.common.exception.ResourceNotFoundException;
import com.enterprise.employee.application.dto.EmployeeRequestDTO;
import com.enterprise.employee.application.dto.EmployeeResponseDTO;
import com.enterprise.employee.application.mapper.EmployeeMapper;
import com.enterprise.employee.application.service.EmployeeService;
import com.enterprise.employee.application.specification.EmployeeSpecifications;
import com.enterprise.employee.domain.model.Employee;
import com.enterprise.employee.domain.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO requestDTO) {
        validateUniqueEmail(requestDTO.email());
        Employee employee = employeeMapper.toEntity(requestDTO);
        employee.setDeleted(false);
        Employee saved = employeeRepository.save(employee);
        log.info("Created employee with id={}", saved.getId());
        return employeeMapper.toResponse(saved);
    }

    @Override
    @Transactional
    public EmployeeResponseDTO updateEmployee(UUID id, EmployeeRequestDTO requestDTO) {
        Employee employee = findActiveEmployee(id);
        if (!employee.getEmail().equalsIgnoreCase(requestDTO.email()) && employeeRepository.existsByEmailAndDeletedFalse(requestDTO.email())) {
            throw new BusinessException("Employee with email already exists");
        }
        employeeMapper.updateEntity(requestDTO, employee);
        Employee saved = employeeRepository.save(employee);
        log.info("Updated employee with id={}", id);
        return employeeMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public EmployeeResponseDTO getEmployee(UUID id) {
        return employeeMapper.toResponse(findActiveEmployee(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeResponseDTO> searchEmployees(String department, String name, Pageable pageable) {
        Specification<Employee> specification = Specification.where(EmployeeSpecifications.notDeleted())
                .and(EmployeeSpecifications.hasDepartment(department))
                .and(EmployeeSpecifications.hasNameContaining(name));
        return employeeRepository.findAll(specification, pageable).map(employeeMapper::toResponse);
    }

    @Override
    @Transactional
    public void deleteEmployee(UUID id) {
        Employee employee = findActiveEmployee(id);
        employee.setDeleted(true);
        employeeRepository.save(employee);
        log.info("Soft deleted employee with id={}", id);
    }

    private void validateUniqueEmail(String email) {
        if (employeeRepository.existsByEmailAndDeletedFalse(email)) {
            throw new BusinessException("Employee with email already exists");
        }
    }

    private Employee findActiveEmployee(UUID id) {
        return employeeRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found for id=" + id));
    }
}
