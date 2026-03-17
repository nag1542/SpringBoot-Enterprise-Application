package com.enterprise.employee.domain.repository;

import com.enterprise.employee.domain.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID>, JpaSpecificationExecutor<Employee> {
    Optional<Employee> findByIdAndDeletedFalse(UUID id);
    boolean existsByEmailAndDeletedFalse(String email);
}
