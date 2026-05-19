package com.alerts.strategy;

import com.alerts.alert.Alert;
import com.alerts.factory.AlertFactory;
import com.alerts.factory.TriggeredAlertFactory;
import com.alerts.utilities.AlertDataSort;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class TriggeredAlertStrategy implements AlertStrategy {

    private static final double TRIGGERED_VALUE = 1;

    private final AlertFactory alertFactory;

    // Constructor that initializes the alert factory
    public TriggeredAlertStrategy() {
        this.alertFactory = new TriggeredAlertFactory();
    }

    /**
     * Checks the patient's data to generate an alert if the alert value is equal to the triggered value
     * @param patient the patient to check
     * @param patientRecords the list of patient records to analyze
     * @return a list of generated alerts
     */
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