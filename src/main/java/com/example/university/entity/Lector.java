package com.example.university.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Lector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Degree degree;

    private double salary;

    @ManyToMany
    @JoinTable(
        name = "lector_department",
        joinColumns = @JoinColumn(name = "lector_id"),
        inverseJoinColumns = @JoinColumn(name = "department_id")
    )
    private List<Department> departments;

    public Lector() {}

    public Lector(String name, Degree degree, double salary) {
        this.name = name;
        this.degree = degree;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }
}
