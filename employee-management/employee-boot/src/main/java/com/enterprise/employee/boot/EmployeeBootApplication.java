package com.enterprise.employee.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.enterprise")
@EntityScan(basePackages = "com.enterprise.employee.domain.model")
@EnableJpaRepositories(basePackages = "com.enterprise.employee.infrastructure.repository")
public class EmployeeBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeBootApplication.class, args);
    }
}
