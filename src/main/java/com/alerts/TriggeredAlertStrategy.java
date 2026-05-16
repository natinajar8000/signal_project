package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class TriggeredAlertStrategy implements AlertStrategy {

    private static final double TRIGGERED_VALUE = 1;

    private final AlertFactory alertFactory;

    public TriggeredAlertStrategy() {
        this.alertFactory = new TriggeredAlertFactory();
    }

    @Override
    public List<Alert> check(Patient patient, List<PatientRecord> patientRecords) {
        List<Alert> alerts = new ArrayList<>();

        List<PatientRecord> alertRecords =
                AlertDataSort.getRecordsByType(patientRecords, "Alert");

        PatientRecord latestAlert = AlertDataSort.getLatestRecord(alertRecords);

        if (latestAlert == null) {
            return alerts;
        }

        if (latestAlert.getMeasurementValue() == TRIGGERED_VALUE) {
            alerts.add(alertFactory.createAlert(
                    patient.getPatientId(),
                    "Alert triggered at: ",
                    latestAlert.getTimestamp()));
        }

        return alerts;
    }
}