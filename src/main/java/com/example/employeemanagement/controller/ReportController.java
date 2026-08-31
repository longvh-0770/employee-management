package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.EmployeeCountReportResponse;
import com.example.employeemanagement.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final EmployeeService employeeService;

    public ReportController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee-count")
    public ResponseEntity<EmployeeCountReportResponse> getEmployeeCountReport() {
        return ResponseEntity.ok(new EmployeeCountReportResponse(employeeService.getEmployeeCountReport()));
    }

}
