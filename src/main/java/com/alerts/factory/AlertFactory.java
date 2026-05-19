package com.alerts.factory;

import com.alerts.alert.Alert;

// Abstract factory class for creating alerts
public abstract class AlertFactory {
    /**
     * Creates an alert with the given parameters
     * @param patientId 
     * @param condition
     * @param timestamp
     */
    public abstract Alert createAlert(int patientId, String condition, long timestamp);
}
