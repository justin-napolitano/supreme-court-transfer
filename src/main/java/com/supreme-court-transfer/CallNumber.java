package com.example;

public class CallNumber {
    private int id;
    private String callNumber;
    private String externalId;

    // Constructor
    public CallNumber(int id, String callNumber, String externalId) {
        this.id = id;
        this.callNumber = callNumber;
        this.externalId = externalId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
