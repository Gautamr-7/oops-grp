package com.example.student_backend.controller;

import com.example.student_backend.dto.KtuValidationRequest;
import com.example.student_backend.model.Student;
import com.example.student_backend.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @PostMapping("/validate-ktu")
    public ResponseEntity<Map<String, Object>> validateKtuId(@RequestBody KtuValidationRequest request) {
        Optional<Student> studentOptional = studentService.validateStudentEligibility(request.getKtuId());

        if (studentOptional.isPresent()) {
            return ResponseEntity.ok(Map.of("data", studentOptional.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(Map.of("message", "Student ID not found or account already created."));
        }
    }

    // This /register method is updated with the correct logic.
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerStudent(@RequestBody Student registrationData) {
        // Step 1: Fetch the original student from the CSV eligibility list to get the name.
        Optional<Student> originalStudentOpt = studentService.getEligibleStudentById(registrationData.getKtuId());

        if (originalStudentOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(Map.of("message", "Cannot register an ineligible student."));
        }

        Student finalStudent = originalStudentOpt.get(); // This has the correct first and last name.

        // Step 2: Merge the new details from the form into the original record.
        finalStudent.setEmail(registrationData.getEmail());
        finalStudent.setPhone(registrationData.getPhone());
        finalStudent.setPassword(registrationData.getPassword());
        finalStudent.setDob(registrationData.getDob());
        finalStudent.setGender(registrationData.getGender());
        finalStudent.setBloodGroup(registrationData.getBloodGroup());
        finalStudent.setYearOfStudy(registrationData.getYearOfStudy());

        // Step 3: Save the complete, merged student object to the database.
        studentService.registerStudent(finalStudent);

        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(Map.of("message", "Registration successful!"));
    }
}