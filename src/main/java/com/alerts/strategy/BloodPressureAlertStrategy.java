package com.alerts.strategy;

import com.alerts.alert.Alert;
import com.alerts.factory.AlertFactory;
import com.alerts.factory.BloodPressureAlertFactory;
import com.alerts.utilities.AlertDataSort;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BloodPressureAlertStrategy implements AlertStrategy {

    private static final double HIGH_SYSTOLIC_LIMIT = 180;
    private static final double LOW_SYSTOLIC_LIMIT = 90;
    private static final double HIGH_DIASTOLIC_LIMIT = 120;
    private static final double LOW_DIASTOLIC_LIMIT = 60;
    private static final double TREND_CHANGE_LIMIT = 10;

    private final AlertFactory alertFactory;

    public BloodPressureAlertStrategy() {
        this.alertFactory = new BloodPressureAlertFactory();
    }

    @Override
    public List<Alert> check(Patient patient, List<PatientRecord> patientRecords) {
        List<Alert> alerts = new ArrayList<>();

        List<PatientRecord> systolicRecords =
                AlertDataSort.getRecordsByType(patientRecords, "SystolicPressure");

        List<PatientRecord> diastolicRecords =
                AlertDataSort.getRecordsByType(patientRecords, "DiastolicPressure");

        checkCriticalThresholds(patient, systolicRecords, diastolicRecords, alerts);
        checkTrends(patient, systolicRecords, diastolicRecords, alerts);

        return alerts;
    }

    private void checkCriticalThresholds(
            Patient patient,
            List<PatientRecord> systolicRecords,
            List<PatientRecord> diastolicRecords,
            List<Alert> alerts) {

        PatientRecord latestSystolic = AlertDataSort.getLatestRecord(systolicRecords);
        PatientRecord latestDiastolic = AlertDataSort.getLatestRecord(diastolicRecords);

        if (latestSystolic != null) {
            double systolic = latestSystolic.getMeasurementValue();

            if (systolic > HIGH_SYSTOLIC_LIMIT || systolic < LOW_SYSTOLIC_LIMIT) {
                alerts.add(alertFactory.createAlert(
                        patient.getPatientId(),
                        "Critical systolic blood pressure: " + systolic,
                        latestSystolic.getTimestamp()));
            }
        }

        if (latestDiastolic != null) {
            double diastolic = latestDiastolic.getMeasurementValue();

            if (diastolic > HIGH_DIASTOLIC_LIMIT || diastolic < LOW_DIASTOLIC_LIMIT) {
                alerts.add(alertFactory.createAlert(
                        patient.getPatientId(),
                        "Critical diastolic blood pressure: " + diastolic,
                        latestDiastolic.getTimestamp()));
            }
        }
    }

    private void checkTrends(
            Patient patient,
            List<PatientRecord> systolicRecords,
            List<PatientRecord> diastolicRecords,
            List<Alert> alerts) {

        if (hasIncreasingTrend(systolicRecords) || hasIncreasingTrend(diastolicRecords)) {
            alerts.add(alertFactory.createAlert(
                    patient.getPatientId(),
                    "Blood pressure increasing trend detected",
                    System.currentTimeMillis()));
        }

        if (hasDecreasingTrend(systolicRecords) || hasDecreasingTrend(diastolicRecords)) {
            alerts.add(alertFactory.createAlert(
                    patient.getPatientId(),
                    "Blood pressure decreasing trend detected",
                    System.currentTimeMillis()));
        }
    }

    private boolean hasIncreasingTrend(List<PatientRecord> records) {
        if (records.size() < 3) {
            return false;
        }

        records.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        PatientRecord first = records.get(records.size() - 3);
        PatientRecord second = records.get(records.size() - 2);
        PatientRecord third = records.get(records.size() - 1);

        return second.getMeasurementValue() - first.getMeasurementValue() > TREND_CHANGE_LIMIT
                && third.getMeasurementValue() - second.getMeasurementValue() > TREND_CHANGE_LIMIT;
    }

    private boolean hasDecreasingTrend(List<PatientRecord> records) {
        if (records.size() < 3) {
            return false;
        }

        records.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        PatientRecord first = records.get(records.size() - 3);
        PatientRecord second = records.get(records.size() - 2);
        PatientRecord third = records.get(records.size() - 1);

        return first.getMeasurementValue() - second.getMeasurementValue() > TREND_CHANGE_LIMIT
                && second.getMeasurementValue() - third.getMeasurementValue() > TREND_CHANGE_LIMIT;
    }
}