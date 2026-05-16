package com.alerts;

// Represents an alert interface
public interface Alert {
    int getPatientId();

    String getCondition();

    long getTimestamp();
}
