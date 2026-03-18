package com.enterprise.employee.boot.integration;

import com.enterprise.employee.application.dto.EmployeeResponseDTO;
import com.enterprise.employee.application.service.EmployeeService;
import com.enterprise.employee.domain.enums.EmployeeStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void shouldCreateEmployee() throws Exception {
        UUID id = UUID.randomUUID();
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO(
                id, "John", "Doe", "john@corp.com", "IT", BigDecimal.valueOf(1000), EmployeeStatus.ACTIVE, Instant.now(), Instant.now());
        when(employeeService.createEmployee(any())).thenReturn(responseDTO);

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "firstName": "John",
                                  "lastName": "Doe",
                                  "email": "john@corp.com",
                                  "department": "IT",
                                  "salary": 1000,
                                  "status": "ACTIVE"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").value(id.toString()));
    }
}
