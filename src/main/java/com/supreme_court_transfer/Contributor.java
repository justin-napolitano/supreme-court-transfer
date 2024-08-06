package com.supreme_court_transfer;

public class Contributor {
    private int id;
    private String externalId;
    private String contributor;

    // Constructor
    public Contributor(int id, String externalId, String contributor) {
        this.id = id;
        this.externalId = externalId;
        this.contributor = contributor;
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

    public String getContributor() {
        return contributor;
    }

    public void setContributor(String contributor) {
        this.contributor = contributor;
    }
}
