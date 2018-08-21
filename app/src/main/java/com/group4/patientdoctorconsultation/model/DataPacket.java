package com.group4.patientdoctorconsultation.model;

import java.util.List;

public class DataPacket {

    public static final String COLLECTION_NAME = "data_packets";
    public static final String FIELD_DOCTOR_ID = "doctorId";
    public static final String FIELD_PATIENT_ID = "patientId";
    public static final String FIELD_DOCUMENT_REFERENCES = "documentReferences";
    public static final String FIELD_NOTES = "notes";
    public static final String FIELD_COMMENTS = "comments";

    private String doctorId;
    private String patientId;
    private List<String> documentReferences;
    private List<String> notes;
    private List<String> comments;


    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public List<String> getDocumentReferences() {
        return documentReferences;
    }

    public void setDocumentReferences(List<String> documentReferences) {
        this.documentReferences = documentReferences;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
