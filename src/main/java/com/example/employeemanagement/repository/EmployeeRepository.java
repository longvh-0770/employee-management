package com.example.employeemanagement.repository;

import com.example.employeemanagement.dto.DepartmentEmployeeCount;
import com.example.employeemanagement.model.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findByDepartment_NameContainingIgnoreCase(String department);

    List<Employee> findByFullNameContainingIgnoreCaseOrDepartment_NameContainingIgnoreCase(
            String fullNameKeyword, String departmentKeyword);

    @Query("SELECT new com.example.employeemanagement.dto.DepartmentEmployeeCount(e.department.name, COUNT(e)) "
            + "FROM Employee e GROUP BY e.department.name ORDER BY e.department.name")
    List<DepartmentEmployeeCount> countEmployeesByDepartment();

}
