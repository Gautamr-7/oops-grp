package com.example.student_backend.dto;

// This is a Data Transfer Object (DTO) for the validation request.
public class KtuValidationRequest {
    private String ktuId;

    public String getKtuId() {
        return ktuId;
    }
    public void setKtuId(String ktuId) {
        this.ktuId = ktuId;
    }
}