package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeCountReportResponse;
import com.example.employeemanagement.dto.EmployeeStatisticsResponse;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final EmployeeService employeeService;
    private final StatisticsService statisticsService;

    public ReportController(EmployeeService employeeService, StatisticsService statisticsService) {
        this.employeeService = employeeService;
        this.statisticsService = statisticsService;
    }

    @GetMapping("/employee-count")
    public ResponseEntity<EmployeeCountReportResponse> getEmployeeCountReport() {
        return ResponseEntity.ok(new EmployeeCountReportResponse(employeeService.getEmployeeCountReport()));
    }

    @GetMapping("/employee-statistics")
    public ResponseEntity<EmployeeStatisticsResponse> getEmployeeStatistics() {
        return ResponseEntity.ok(statisticsService.getEmployeeStatistics());
    }

}
