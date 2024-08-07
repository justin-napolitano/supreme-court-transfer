package com.supreme_court_transfer;

public class Item {
    private String externalId;
    private String title;
    private String callNumber;
    private String date;

    // Constructor
    public Item(String externalId, String title, String callNumber, String date) {
        this.externalId = externalId;
        this.title = title;
        this.callNumber = callNumber;
        this.date = date;
    }

    // Getters and setters
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
