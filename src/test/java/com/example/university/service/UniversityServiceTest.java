package com.example.university.service;

import com.example.university.entity.Department;
import com.example.university.entity.Lector;
import com.example.university.entity.Degree;
import com.example.university.repository.DepartmentRepository;
import com.example.university.repository.LectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversityServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private LectorRepository lectorRepository;

    @InjectMocks
    private UniversityService universityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetHeadOfDepartment() {
        Department department = new Department("Computer Science", new Lector("Oleg Sidorov", Degree.PROFESSOR, 8000.0));
        when(departmentRepository.findByName("Computer Science")).thenReturn(Optional.of(department));

        String result = universityService.getHeadOfDepartment("Computer Science");

        assertEquals("Head of Computer Science department is Oleg Sidorov", result);
        verify(departmentRepository, times(1)).findByName("Computer Science");
    }

    @Test
    void testGetDepartmentStatistics() {
        Lector lector1 = new Lector("Ivan Petrenko", Degree.ASSISTANT, 3000.0);
        Lector lector2 = new Lector("Maria Ivanova", Degree.ASSOCIATE_PROFESSOR, 5000.0);
        Lector lector3 = new Lector("Oleg Sidorov", Degree.PROFESSOR, 8000.0);
        Department department = new Department("Computer Science", lector3);
        department.setLectors(List.of(lector1, lector2, lector3));

        when(departmentRepository.findByName("Computer Science")).thenReturn(Optional.of(department));

        String result = universityService.getDepartmentStatistics("Computer Science");

        assertEquals("assistants - 1, associate professors - 1, professors - 1", result);
        verify(departmentRepository, times(1)).findByName("Computer Science");
    }

    @Test
    void testGetAverageSalary() {
        Lector lector1 = new Lector("Ivan Petrenko", Degree.ASSISTANT, 3000.0);
        Lector lector2 = new Lector("Maria Ivanova", Degree.ASSOCIATE_PROFESSOR, 5000.0);
        Lector lector3 = new Lector("Oleg Sidorov", Degree.PROFESSOR, 8000.0);
        Lector lector4 = new Lector("Natalia Kvitka", Degree.ASSISTANT, 4000.0);
        Department department = new Department("Mathematics", lector3);
        department.setLectors(List.of(lector1, lector2, lector3, lector4));

        when(departmentRepository.findByName("Mathematics")).thenReturn(Optional.of(department));

        Double result = universityService.getAverageSalary("Mathematics");

        assertEquals(5000, result);
        verify(departmentRepository, times(1)).findByName("Mathematics");
    }
}
