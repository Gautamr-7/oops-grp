package com.example.student_backend.service;

import com.example.student_backend.model.Student;
import com.example.student_backend.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final List<Student> eligibleStudents = new ArrayList<>();
    
    @Autowired
    private StudentRepository studentRepository;

    public StudentService() {
        loadStudentsFromCSV();
    }

    private void loadStudentsFromCSV() {
        String filePath = "/students.csv";
        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

            reader.readLine(); // Skip header

            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",", -1);
                if (data.length >= 5) {
                    eligibleStudents.add(new Student(data[0].trim(), data[1].trim(), data[2].trim(), data[3].trim(), data[4].trim()));
                }
            }
            System.out.println("Loaded " + eligibleStudents.size() + " eligible students from CSV into memory.");
        } catch (Exception e) {
            System.err.println("Error loading students from CSV: " + e.getMessage());
        }
    }

    public Optional<Student> validateStudentEligibility(String ktuId) {
        if (studentRepository.existsByKtuIdIgnoreCase(ktuId)) {
            return Optional.empty(); 
        }
        return eligibleStudents.stream()
                .filter(student -> student.getKtuId().equalsIgnoreCase(ktuId))
                .findFirst();
    }
    
    // This new method is added to help the controller fetch the original student details.
    public Optional<Student> getEligibleStudentById(String ktuId) {
        return eligibleStudents.stream()
                .filter(student -> student.getKtuId().equalsIgnoreCase(ktuId))
                .findFirst();
    }

    public void registerStudent(Student student) {
        studentRepository.save(student);
    }
}