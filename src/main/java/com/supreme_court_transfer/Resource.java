package com.supreme_court_transfer;

public class Resource {
    private int id;
    private String pdf;
    private String image;
    private String externalId;

    // Constructor
    public Resource(int id, String pdf, String image, String externalId) {
        this.id = id;
        this.pdf = pdf;
        this.image = image;
        this.externalId = externalId;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
}
