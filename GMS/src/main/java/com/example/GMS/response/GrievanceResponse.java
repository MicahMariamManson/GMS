package com.example.GMS.response;

import java.time.LocalDateTime;

public class GrievanceResponse {

    private Long grievanceId;
    private String description;
    private String status;
    private LocalDateTime submittedDate;

    // Constructor
    public GrievanceResponse(Long grievanceId, String description, String status, LocalDateTime submittedDate) {
        this.grievanceId = grievanceId;
        this.description = description;
        this.status = status;
        this.submittedDate = submittedDate;
    }

    // Getters and Setters
    public Long getGrievanceId() {
        return grievanceId;
    }

    public void setGrievanceId(Long grievanceId) {
        this.grievanceId = grievanceId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(LocalDateTime submittedDate) {
        this.submittedDate = submittedDate;
    }
}