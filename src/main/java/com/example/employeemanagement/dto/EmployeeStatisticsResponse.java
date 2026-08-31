package com.example.employeemanagement.dto;

import java.util.List;

public record EmployeeStatisticsResponse(long totalEmployees, List<DepartmentEmployeeCount> byDepartment) {
}
