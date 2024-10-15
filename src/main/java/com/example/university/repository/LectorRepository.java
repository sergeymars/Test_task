package com.example.university.repository;

import com.example.university.entity.Lector;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LectorRepository extends JpaRepository<Lector, Long> {
    List<Lector> findByNameContaining(String name);
}
