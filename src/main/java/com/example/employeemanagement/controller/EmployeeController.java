package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeRegistrationResult;
import com.example.employeemanagement.service.EmployeeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/employees/register")
    public EmployeeRegistrationResult register(
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String password) {
        return employeeService.register(firstName, lastName, password);
    }

}
