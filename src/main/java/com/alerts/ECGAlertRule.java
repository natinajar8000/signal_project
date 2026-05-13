package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ECGAlertRule implements AlertRule {

    private static final int WINDOW_SIZE = 10;
    private static final double PEAK_MULTIPLIER = 2.0;
    private static final double MINIMUM_PEAK_DIFFERENCE = 0.5;

    @Override
    public List<Alert> check(Patient patient, List<PatientRecord> patientRecords) {
        List<Alert> alerts = new ArrayList<>();

        List<PatientRecord> ecgRecords =
                AlertDataSort.getRecordsByType(patientRecords, "ECG");

        if (ecgRecords.size() < WINDOW_SIZE) {
            return alerts;
        }

        ecgRecords.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        int startIndex = ecgRecords.size() - WINDOW_SIZE;
        List<PatientRecord> window =
                ecgRecords.subList(startIndex, ecgRecords.size());

        double average = calculateAverageAbsoluteValue(window);

        PatientRecord latestRecord = window.get(window.size() - 1);
        double latestValue = Math.abs(latestRecord.getMeasurementValue());

        if (latestValue > average * PEAK_MULTIPLIER
                && latestValue > average + MINIMUM_PEAK_DIFFERENCE) {

            alerts.add(new Alert(
                    patient.getPatientId(),
                    "Abnormal ECG peak detected: "
                            + latestRecord.getMeasurementValue(),
                    latestRecord.getTimestamp()));
        }

        return alerts;
    }

    private double calculateAverageAbsoluteValue(List<PatientRecord> records) {
        double sum = 0;

        for (PatientRecord record : records) {
            sum += Math.abs(record.getMeasurementValue());
        }

        return sum / records.size();
    }
}