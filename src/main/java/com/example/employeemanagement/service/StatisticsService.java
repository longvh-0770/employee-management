package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.EmployeeStatisticsResponse;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

@Service
public class StatisticsService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeService employeeService;

    public StatisticsService(EmployeeRepository employeeRepository, EmployeeService employeeService) {
        this.employeeRepository = employeeRepository;
        this.employeeService = employeeService;
    }

    public EmployeeStatisticsResponse getEmployeeStatistics() {
        long totalEmployees = employeeService.getEmployeeCountReport();
        return new EmployeeStatisticsResponse(totalEmployees, employeeRepository.countEmployeesByDepartment());
    }

}
