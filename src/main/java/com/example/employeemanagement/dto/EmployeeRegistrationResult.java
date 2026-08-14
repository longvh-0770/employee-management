package com.example.employeemanagement.dto;

public record EmployeeRegistrationResult(
        String employeeCode,
        String fullName,
        String encodedPassword) {
}
