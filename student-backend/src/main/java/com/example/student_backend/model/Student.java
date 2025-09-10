package com.example.student_backend.model;

import com.fasterxml.jackson.annotation.JsonProperty; // Import this
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Student {

    @Id
    private String ktuId;

    private String firstname;
    private String lastname;
    private String branch;
    private String batch;

    private String email;
    private String phone;
    
    // ADD THIS ANNOTATION to the password field
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    private LocalDate dob;
    private String gender;
    private String bloodGroup;
    private int yearOfStudy;

    public Student() {}

    public Student(String ktuId, String firstname, String lastname, String branch, String batch) {
        this.ktuId = ktuId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.branch = branch;
        this.batch = batch;
    }

    @JsonProperty("fullName")
    public String getFullName() {
        String first = (firstname != null) ? firstname.trim() : "";
        String last = (lastname != null) ? lastname.trim() : "";
        return (first + " " + last).trim();
    }

    // --- All Getters and Setters remain the same ---
    // (No changes needed for them)
    public String getKtuId() { return ktuId; }
    public void setKtuId(String ktuId) { this.ktuId = ktuId; }
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }
    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }
    public String getBatch() { return batch; }
    public void setBatch(String batch) { this.batch = batch; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public LocalDate getDob() { return dob; }
    public void setDob(LocalDate dob) { this.dob = dob; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getBloodGroup() { return bloodGroup; }
    public void setBloodGroup(String bloodGroup) { this.bloodGroup = bloodGroup; }
    public int getYearOfStudy() { return yearOfStudy; }
    public void setYearOfStudy(int yearOfStudy) { this.yearOfStudy = yearOfStudy; }
}