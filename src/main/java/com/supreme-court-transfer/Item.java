package com.example;

public class Item {
    private int id;
    private String notes;
    private String callNumber;
    private String createdPublished;
    private String title;
    private String date;
    private String sourceCollection;
    private String externalId;

    // Constructor
    public Item(int id, String notes, String callNumber, String createdPublished, String title, String date, String sourceCollection, String externalId) {
        this.id = id;
        this.notes = notes;
        this.callNumber = callNumber;
        this.createdPublished = createdPublished;
        this.title = title;
        this.date = date;
        this.sourceCollection = sourceCollection;
        this.externalId = externalId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCreatedPublished() {
        return createdPublished;
    }

    public void setCreatedPublished(String createdPublished) {
        this.createdPublished = createdPublished;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSourceCollection() {
        return sourceCollection;
    }

    public void setSourceCollection(String sourceCollection) {
        this.sourceCollection = sourceCollection;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
