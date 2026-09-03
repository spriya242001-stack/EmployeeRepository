package com.example.demoEmployee.controller;

import com.example.demoEmployee.model.Employee;
import com.example.demoEmployee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    // CREATE (POST /employees)
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }
    // READ ALL (GET /employees/displayAll)
    @GetMapping("/displayAll")
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    // READ BY ID (GET /employees/display/{employeeId})
    @GetMapping("display/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") String employeeId) {
        return employeeRepository.findById(employeeId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // UPDATE (PUT /employees/update/{employeeId})
    @PutMapping("/update/{employeeId}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("employeeId") String employeeId, @RequestBody Employee updatedDetails) {
        return employeeRepository.findById(employeeId).map(existing -> {
            existing.setEmployeeName(updatedDetails.getEmployeeName());
            existing.setEmployeeEmail(updatedDetails.getEmployeeEmail());
            existing.setLocation(updatedDetails.getLocation());
            Employee saved = employeeRepository.save(existing);
            return ResponseEntity.ok(saved);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // DELETE (DELETE /employees/delete/{employeeId})
    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("employeeId") String employeeId) {
        if (employeeRepository.existsById(employeeId)) {
            employeeRepository.deleteById(employeeId);
            return ResponseEntity.ok("Employee deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }
}