package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeRegistrationResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final UtilityService utilityService;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(UtilityService utilityService, PasswordEncoder passwordEncoder) {
        this.utilityService = utilityService;
        this.passwordEncoder = passwordEncoder;
    }

    public EmployeeRegistrationResult register(String firstName, String lastName, String rawPassword) {
        String fullName = utilityService.formatFullName(firstName, lastName);
        String employeeCode = utilityService.generateEmployeeCode(firstName, lastName);
        String encodedPassword = passwordEncoder.encode(rawPassword);
        return new EmployeeRegistrationResult(employeeCode, fullName, encodedPassword);
    }

}
