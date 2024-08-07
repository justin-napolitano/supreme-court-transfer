package com.supreme_court_transfer;

public class CallNumber {
    private String externalId;
    private String callNumber;

    // Constructor
    public CallNumber(String externalId, String callNumber) {
        this.externalId = externalId;
        this.callNumber = callNumber;
    }

    // Getters and setters
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }
}
