package com.example.employeemanagement.dto;

import com.example.employeemanagement.model.Employee;

public record EmployeeResponse(
        Long id,
        String employeeCode,
        String fullName,
        String email,
        Long departmentId,
        String departmentName) {

    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(
                employee.getId(),
                employee.getEmployeeCode(),
                employee.getFullName(),
                employee.getEmail(),
                employee.getDepartment() == null ? null : employee.getDepartment().getId(),
                employee.getDepartment() == null ? null : employee.getDepartment().getName());
    }

}
