package com.alerts.alert;

//A basic implementation of the alert interface
public class BasicAlert implements Alert {
    private int patientId;
    private String condition;
    private long timestamp;

    /**
     * Constructs a new basic alert with the specified details.
     *
     * @param patientId the ID of the patient associated with the alert
     * @param condition a description of the alert condition
     * @param timestamp the time the alert occurred in milliseconds
     */
    public BasicAlert(int patientId, String condition, long timestamp) {
        this.patientId = patientId;
        this.condition = condition;
        this.timestamp = timestamp;
    }

    @Override
    public int getPatientId() {
        return patientId;
    }

    @Override
    public String getCondition() {
        return condition;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }
}