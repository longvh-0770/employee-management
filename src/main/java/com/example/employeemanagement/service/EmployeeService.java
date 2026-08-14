package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.CreateEmployeeRequest;
import com.example.employeemanagement.dto.EmployeeResponse;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final UtilityService utilityService;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            UtilityService utilityService,
            PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.utilityService = utilityService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<EmployeeResponse> getAllEmployees(String department) {
        return employeeRepository.findAll().stream()
                .filter(employee -> department == null || department.equalsIgnoreCase(employee.department()))
                .map(EmployeeResponse::from)
                .toList();
    }

    public Optional<EmployeeResponse> getEmployeeById(Long id) {
        return employeeRepository.findById(id).map(EmployeeResponse::from);
    }

    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        String fullName = utilityService.formatFullName(request.firstName(), request.lastName());
        String employeeCode = utilityService.generateEmployeeCode(request.firstName(), request.lastName());
        String encodedPassword = passwordEncoder.encode(request.password());

        Employee employee = new Employee(
                null,
                employeeCode,
                fullName,
                request.email(),
                request.department(),
                encodedPassword);

        Employee saved = employeeRepository.save(employee);
        return EmployeeResponse.from(saved);
    }

}
