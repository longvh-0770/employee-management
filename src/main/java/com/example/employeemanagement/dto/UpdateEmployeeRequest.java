package com.example.employeemanagement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateEmployeeRequest(
        @NotBlank(message = "firstName không được để trống") String firstName,
        @NotBlank(message = "lastName không được để trống") String lastName,
        @NotBlank(message = "email không được để trống")
        @Email(message = "email không đúng định dạng") String email,
        @NotNull(message = "departmentId không được để trống") Long departmentId) {
}
