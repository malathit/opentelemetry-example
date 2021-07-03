package com.example.telemetry.spring.controller;

import com.example.telemetry.spring.models.Employee;
import com.example.telemetry.spring.models.EmployeeRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController(value = "/employee")
@AllArgsConstructor
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    @PostMapping
    public ResponseEntity<String> createEmployee(EmployeeDto employee) {
        Employee emp = employeeRepository.save(Employee.builder().name(employee.getName()).build());
        return new ResponseEntity<>("{\"id\": \"" + emp.getId() + "\"}", HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<Page<Employee>> listEmployee(@RequestParam(name = "size", defaultValue = "10") int size,
        @RequestParam(name = "page", defaultValue = "0") int page) {
        return new ResponseEntity<>(employeeRepository.findAll(Pageable.ofSize(10)), HttpStatus.OK);
    }
}
