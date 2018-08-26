package com.group4.patientdoctorconsultation.model;

public abstract class FirestoreResourceModel {

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
