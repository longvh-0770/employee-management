package com.example.employeemanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "username không được để trống") String username,
        @NotBlank(message = "password không được để trống")
        @Size(min = 6, message = "password phải có ít nhất 6 ký tự") String password) {
}
