package com.alerts;

public class TriggeredAlertFactory extends AlertFactory {

    @Override
    public Alert createAlert(int patientId, String condition, long timestamp) {
        return new BasicAlert(patientId, condition, timestamp);
    }

}
