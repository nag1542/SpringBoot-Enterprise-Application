package com.enterprise.employee.application.specification;

import com.enterprise.employee.domain.model.Employee;
import org.springframework.data.jpa.domain.Specification;

public final class EmployeeSpecifications {

    private EmployeeSpecifications() {
    }

    public static Specification<Employee> notDeleted() {
        return (root, query, cb) -> cb.isFalse(root.get("deleted"));
    }

    public static Specification<Employee> hasDepartment(String department) {
        return (root, query, cb) -> department == null || department.isBlank()
                ? cb.conjunction()
                : cb.equal(cb.lower(root.get("department")), department.toLowerCase());
    }

    public static Specification<Employee> hasNameContaining(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) {
                return cb.conjunction();
            }
            String like = "%" + name.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("firstName")), like),
                    cb.like(cb.lower(root.get("lastName")), like)
            );
        };
    }
}
