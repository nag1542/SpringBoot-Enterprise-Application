package com.enterprise.employee.application.mapper;

import com.enterprise.employee.application.dto.EmployeeRequestDTO;
import com.enterprise.employee.application.dto.EmployeeResponseDTO;
import com.enterprise.employee.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEntity(EmployeeRequestDTO dto);

    EmployeeResponseDTO toResponse(Employee employee);

    void updateEntity(EmployeeRequestDTO dto, @MappingTarget Employee employee);
}
