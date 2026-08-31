package com.example.employeemanagement.controller;

import com.example.employeemanagement.dto.CreateEmployeeRequest;
import com.example.employeemanagement.dto.EmployeeFormRequest;
import com.example.employeemanagement.dto.EmployeeResponse;
import com.example.employeemanagement.service.DepartmentService;
import com.example.employeemanagement.service.EmployeeService;
import com.example.employeemanagement.service.StatisticsService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/employees")
public class EmployeeViewController {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final StatisticsService statisticsService;

    public EmployeeViewController(
            EmployeeService employeeService, DepartmentService departmentService, StatisticsService statisticsService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.statisticsService = statisticsService;
    }

    @GetMapping("/list")
    public String listEmployees(@RequestParam(required = false) String keyword, Model model) {
        List<EmployeeResponse> employees = (keyword == null || keyword.isBlank())
                ? employeeService.getAllEmployees(null)
                : employeeService.searchEmployees(keyword);
        model.addAttribute("employees", employees);
        model.addAttribute("keyword", keyword);
        return "employees/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("employeeForm", new EmployeeFormRequest());
        model.addAttribute("departments", departmentService.getAllDepartments());
        return "employees/add";
    }

    @PostMapping("/add")
    public String addEmployee(
            @Valid @ModelAttribute("employeeForm") EmployeeFormRequest form,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("departments", departmentService.getAllDepartments());
            return "employees/add";
        }

        CreateEmployeeRequest request = new CreateEmployeeRequest(
                form.getFirstName(), form.getLastName(), form.getEmail(), form.getDepartmentId(), form.getPassword());
        employeeService.createEmployee(request);
        return "redirect:/employees/list";
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        model.addAttribute("statistics", statisticsService.getEmployeeStatistics());
        return "employees/statistics";
    }

}
