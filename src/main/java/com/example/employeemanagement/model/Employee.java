package com.example.employeemanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String employeeCode;

    @Column(nullable = false)
    private String fullName;

    private String email;

    @Column(nullable = false)
    private String encodedPassword;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    protected Employee() {
    }

    public Employee(String employeeCode, String fullName, String email, String encodedPassword, Department department) {
        this.employeeCode = employeeCode;
        this.fullName = fullName;
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.department = department;
    }

    public Long getId() {
        return id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getEncodedPassword() {
        return encodedPassword;
    }

    public Department getDepartment() {
        return department;
    }

}
