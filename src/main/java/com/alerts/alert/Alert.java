package com.alerts.alert;

// Represents an alert interface
public interface Alert {
    int getPatientId();

    String getCondition();

    long getTimestamp();
}
