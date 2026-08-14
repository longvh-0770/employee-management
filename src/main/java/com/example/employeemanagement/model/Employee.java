package com.example.employeemanagement.model;

public record Employee(
        Long id,
        String employeeCode,
        String fullName,
        String email,
        String department,
        String encodedPassword) {
}
