package com.example.university.service;

import com.example.university.entity.Degree;
import com.example.university.entity.Department;
import com.example.university.entity.Lector;
import com.example.university.repository.DepartmentRepository;
import com.example.university.repository.LectorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UniversityService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private LectorRepository lectorRepository;

    public String getHeadOfDepartment(String departmentName) {
        return departmentRepository.findByName(departmentName)
            .map(department -> "Head of " + departmentName + " department is " + department.getHeadOfDepartment().getName())
            .orElse("Department not found");
    }
    @Transactional
    public String getDepartmentStatistics(String departmentName) {
        Department department = departmentRepository.findByName(departmentName)
            .orElseThrow(() -> new RuntimeException("Department not found"));
        
        long assistants = department.getLectors().stream().filter(lector -> lector.getDegree() == Degree.ASSISTANT).count();
        long associateProfessors = department.getLectors().stream().filter(lector -> lector.getDegree() == Degree.ASSOCIATE_PROFESSOR).count();
        long professors = department.getLectors().stream().filter(lector -> lector.getDegree() == Degree.PROFESSOR).count();

        return String.format("assistants - %d, associate professors - %d, professors - %d", assistants, associateProfessors, professors);
    }
    @Transactional
    public double getAverageSalary(String departmentName) {
        Department department = departmentRepository.findByName(departmentName)
            .orElseThrow(() -> new RuntimeException("Department not found"));
        
        return department.getLectors().stream().mapToDouble(Lector::getSalary).average().orElse(0);
    }
    @Transactional
    public long getEmployeeCount(String departmentName) {
        return departmentRepository.findByName(departmentName)
            .map(department -> (long) department.getLectors().size())
            .orElse(0L);
    }

    public List<String> getAllDepartments() {
        return departmentRepository.findAll().stream().map(Department::getName).collect(Collectors.toList());
    }

    public List<String> getAllLectors() {
        return lectorRepository.findAll().stream().map(Lector::getName).collect(Collectors.toList());
    }

    public List<String> globalSearch(String template) {
        List<String> foundLectors = lectorRepository.findByNameContaining(template)
                .stream()
                .map(lector -> "Lector: " + lector.getName())
                .collect(Collectors.toList());

        List<String> foundDepartments = departmentRepository.findByNameContaining(template)
                .stream()
                .map(department -> "Department: " + department.getName())
                .collect(Collectors.toList());

        foundLectors.addAll(foundDepartments);
        return foundLectors;
    }
}
