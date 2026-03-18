package com.enterprise.employee.application.service;

import com.enterprise.common.exception.BusinessException;
import com.enterprise.employee.application.dto.EmployeeRequestDTO;
import com.enterprise.employee.application.mapper.EmployeeMapper;
import com.enterprise.employee.application.service.impl.EmployeeServiceImpl;
import com.enterprise.employee.domain.enums.EmployeeStatus;
import com.enterprise.employee.domain.model.Employee;
import com.enterprise.employee.domain.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private EmployeeMapper employeeMapper;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Test
    void shouldThrowBusinessExceptionWhenEmailAlreadyExists() {
        EmployeeRequestDTO request = new EmployeeRequestDTO("John", "Doe", "john@corp.com", "IT", BigDecimal.TEN, EmployeeStatus.ACTIVE);
        when(employeeRepository.existsByEmailAndDeletedFalse(request.email())).thenReturn(true);

        assertThrows(BusinessException.class, () -> employeeService.createEmployee(request));
    }

    @Test
    void shouldSoftDeleteEmployee() {
        Employee employee = Employee.builder().email("john@corp.com").deleted(false).build();
        when(employeeRepository.findByIdAndDeletedFalse(org.mockito.ArgumentMatchers.any())).thenReturn(java.util.Optional.of(employee));

        employeeService.deleteEmployee(java.util.UUID.randomUUID());

        org.junit.jupiter.api.Assertions.assertTrue(employee.isDeleted());
    }
}
