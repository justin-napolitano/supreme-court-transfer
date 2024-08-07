package com.supreme_court_transfer;

public class Resource {
    private String externalId;
    private String pdf;
    private String image;

    // Constructor
    public Resource(String externalId, String pdf, String image) {
        this.externalId = externalId;
        this.pdf = pdf;
        this.image = image;
    }

    // Getters and setters
    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
