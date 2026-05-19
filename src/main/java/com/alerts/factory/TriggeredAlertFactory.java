package com.alerts.factory;

import com.alerts.alert.Alert;
import com.alerts.alert.BasicAlert;

public class TriggeredAlertFactory extends AlertFactory {

    /**
     * Creates a triggered alert with the given parameters
     * @param patientId 
     * @param condition 
     * @param timestamp 
     * @return a new alert
     */
    @Override
    public Alert createAlert(int patientId, String condition, long timestamp) {
        return new BasicAlert(patientId, condition, timestamp);
    }

}
