package com.group4.patientdoctorconsultation.model;

import com.google.common.base.Joiner;

import java.util.List;

public class DataPacket extends FirestoreResourceModel{

    public static final String COLLECTION_NAME = "data_packets";
    public static final String FIELD_DOCTOR_ID = "doctorId";
    public static final String FIELD_PATIENT_ID = "patientId";
    public static final String FIELD_DOCUMENT_REFERENCES = "documentReferences";
    public static final String FIELD_NOTES = "notes";
    public static final String FIELD_COMMENTS = "comments";
    public static final String FIELD_TITLE = "title";
    public static final String FIELD_HEART_RATE = "heartRate";

    private String doctorId;
    private String patientId;
    private String title;
    private List<String> documentReferences;
    private List<String> notes;
    private List<String> comments;
    private String heartRate;


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getNoteString(){
        return Joiner.on("\n").skipNulls().join(notes);
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public List<String> getComments() {
        return comments;
    }

    public String getCommentString(){
        return Joiner.on("\n").skipNulls().join(comments);
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }
}