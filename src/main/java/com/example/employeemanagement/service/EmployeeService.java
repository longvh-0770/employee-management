package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.CreateEmployeeRequest;
import com.example.employeemanagement.dto.EmployeeResponse;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final UtilityService utilityService;
    private final PasswordEncoder passwordEncoder;

    public EmployeeService(
            EmployeeRepository employeeRepository,
            DepartmentRepository departmentRepository,
            UtilityService utilityService,
            PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.utilityService = utilityService;
        this.passwordEncoder = passwordEncoder;
    }

    public List<EmployeeResponse> getAllEmployees(String department) {
        List<Employee> employees = department == null
                ? employeeRepository.findAll()
                : employeeRepository.findByDepartment_NameContainingIgnoreCase(department);
        return employees.stream().map(EmployeeResponse::from).toList();
    }

    public EmployeeResponse getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return EmployeeResponse.from(employee);
    }

    public List<EmployeeResponse> searchEmployees(String keyword) {
        return employeeRepository
                .findByFullNameContainingIgnoreCaseOrDepartment_NameContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(EmployeeResponse::from)
                .toList();
    }

    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        Department department = departmentRepository.findById(request.departmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy phòng ban với id " + request.departmentId()));

        String fullName = utilityService.formatFullName(request.firstName(), request.lastName());
        String employeeCode = utilityService.generateEmployeeCode(request.firstName(), request.lastName());
        String encodedPassword = passwordEncoder.encode(request.password());

        Employee employee = new Employee(employeeCode, fullName, request.email(), encodedPassword, department);
        Employee saved = employeeRepository.save(employee);
        return EmployeeResponse.from(saved);
    }

}
