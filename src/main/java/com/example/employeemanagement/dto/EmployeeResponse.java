package com.example.employeemanagement.dto;

import com.example.employeemanagement.model.Employee;

public record EmployeeResponse(
        Long id,
        String employeeCode,
        String fullName,
        String email,
        String department) {

    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(
                employee.id(),
                employee.employeeCode(),
                employee.fullName(),
                employee.email(),
                employee.department());
    }

}
