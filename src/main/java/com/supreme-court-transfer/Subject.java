package com.example;

public class Subject {
    private int id;
    private String externalId;
    private String subject;

    // Constructor
    public Subject(int id, String externalId, String subject) {
        this.id = id;
        this.externalId = externalId;
        this.subject = subject;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
