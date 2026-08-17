package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.CreateDepartmentRequest;
import com.example.employeemanagement.dto.DepartmentResponse;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.repository.DepartmentRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(DepartmentResponse::from)
                .toList();
    }

    public DepartmentResponse createDepartment(CreateDepartmentRequest request) {
        Department saved = departmentRepository.save(new Department(request.name()));
        return DepartmentResponse.from(saved);
    }

}
