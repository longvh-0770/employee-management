package com.example.employeemanagement.service;

import com.example.employeemanagement.dto.CreateEmployeeRequest;
import com.example.employeemanagement.dto.EmployeeResponse;
import com.example.employeemanagement.dto.UpdateEmployeeRequest;
import com.example.employeemanagement.exception.EmployeeNotFoundException;
import com.example.employeemanagement.model.Department;
import com.example.employeemanagement.model.Employee;
import com.example.employeemanagement.repository.DepartmentRepository;
import com.example.employeemanagement.repository.EmployeeRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeService.class);

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
        Department department = findDepartmentOrThrow(request.departmentId());

        String fullName = utilityService.formatFullName(request.firstName(), request.lastName());
        String employeeCode = utilityService.generateEmployeeCode(request.firstName(), request.lastName());
        String encodedPassword = passwordEncoder.encode(request.password());

        Employee employee = new Employee(employeeCode, fullName, request.email(), encodedPassword, department);
        Employee saved = employeeRepository.save(employee);
        log.info("Đã thêm nhân viên: id={}, employeeCode={}", saved.getId(), saved.getEmployeeCode());
        return EmployeeResponse.from(saved);
    }

    public EmployeeResponse updateEmployee(Long id, UpdateEmployeeRequest request) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        Department department = findDepartmentOrThrow(request.departmentId());

        employee.setFullName(utilityService.formatFullName(request.firstName(), request.lastName()));
        employee.setEmail(request.email());
        employee.setDepartment(department);

        Employee saved = employeeRepository.save(employee);
        log.info("Đã cập nhật nhân viên: id={}, employeeCode={}", saved.getId(), saved.getEmployeeCode());
        return EmployeeResponse.from(saved);
    }

    public void deleteEmployee(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        employeeRepository.delete(employee);
        log.info("Đã xóa nhân viên: id={}, employeeCode={}", employee.getId(), employee.getEmployeeCode());
    }

    private Department findDepartmentOrThrow(Long departmentId) {
        return departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Không tìm thấy phòng ban với id " + departmentId));
    }

}
