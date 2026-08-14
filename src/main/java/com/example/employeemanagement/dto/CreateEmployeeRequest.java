package com.example.employeemanagement.dto;

public record CreateEmployeeRequest(
        String firstName,
        String lastName,
        String email,
        String department,
        String password) {
}
