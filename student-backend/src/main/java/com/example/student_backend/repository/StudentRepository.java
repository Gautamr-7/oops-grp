package com.example.student_backend.repository;

import com.example.student_backend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    
    // Method to check if a student is already registered in the database
    boolean existsByKtuIdIgnoreCase(String ktuId);
}