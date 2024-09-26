package com.example.GMS.request;


public class GrievanceRequest {
    private String title;
    private String description;
    private String type;
    private Long userId;

    // Constructors
    public GrievanceRequest() {}

    public GrievanceRequest(String title, String description, String type, Long userId) {
        this.title = title;
        this.description = description;
        this.type = type;
        this.userId = userId;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}