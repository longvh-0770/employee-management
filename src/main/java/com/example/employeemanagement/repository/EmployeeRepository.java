package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment_NameContainingIgnoreCase(String department);

    List<Employee> findByFullNameContainingIgnoreCaseOrDepartment_NameContainingIgnoreCase(
            String fullNameKeyword, String departmentKeyword);

}
