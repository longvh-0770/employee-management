package com.example.employeemanagement.repository;

import com.example.employeemanagement.model.Employee;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeRepository {

    private final Map<Long, Employee> store = new ConcurrentHashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    public List<Employee> findAll() {
        return List.copyOf(store.values());
    }

    public Optional<Employee> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    public Employee save(Employee employee) {
        long id = idSequence.getAndIncrement();
        Employee saved = new Employee(
                id,
                employee.employeeCode(),
                employee.fullName(),
                employee.email(),
                employee.department(),
                employee.encodedPassword());
        store.put(id, saved);
        return saved;
    }

}
