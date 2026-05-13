package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class TriggeredAlertRule implements AlertRule {

    private static final double TRIGGERED_VALUE = 1;

    @Override
    public List<Alert> check(Patient patient, List<PatientRecord> patientRecords) {
        List<Alert> alerts = new ArrayList<>();

        List<PatientRecord> alertRecords =
                AlertDataSort.getRecordsByType(patient, "Alert");

        PatientRecord latestAlert = AlertDataSort.getLatestRecord(alertRecords);

        if (latestAlert == null) {
            return alerts;
        }

        if (latestAlert.getMeasurementValue() == TRIGGERED_VALUE) {
            alerts.add(new Alert(
                    String.valueOf(patient.getPatientId()),
                    "Alert triggered at: ",
                    latestAlert.getTimestamp()));
        }

        return alerts;
    }
}