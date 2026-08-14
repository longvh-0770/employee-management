package com.example.employeemanagement.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.stereotype.Service;

@Service
public class UtilityService {

    private final AtomicInteger sequence = new AtomicInteger(1);

    public String formatFullName(String firstName, String lastName) {
        String first = capitalize(firstName.trim());
        String last = capitalize(lastName.trim());
        return first + " " + last;
    }

    public String generateEmployeeCode(String firstName, String lastName) {
        String initials = ("" + firstName.charAt(0) + lastName.charAt(0)).toUpperCase();
        int number = sequence.getAndIncrement();
        return String.format("EMP-%s-%03d", initials, number);
    }

    private String capitalize(String value) {
        if (value.isEmpty()) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }

}
