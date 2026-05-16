package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class HypotensiveHypoxemiaStrategy implements AlertStrategy {

    private static final double LOW_SYSTOLIC_LIMIT = 90;
    private static final double LOW_SATURATION_LIMIT = 92;

    private final AlertFactory alertFactory;

    public HypotensiveHypoxemiaStrategy() {
        this.alertFactory = new HypotensiveHypoxemiaAlertFactory();
    }

    @Override
    public List<Alert> check(Patient patient, List<PatientRecord> patientRecords) {
        List<Alert> alerts = new ArrayList<>();

        List<PatientRecord> systolicRecords =
                AlertDataSort.getRecordsByType(patientRecords, "SystolicPressure");

        List<PatientRecord> saturationRecords =
                AlertDataSort.getRecordsByType(patientRecords, "Saturation");

        PatientRecord latestSystolic = AlertDataSort.getLatestRecord(systolicRecords);
        PatientRecord latestSaturation = AlertDataSort.getLatestRecord(saturationRecords);

        if (latestSystolic == null || latestSaturation == null) {
            return alerts;
        }

        double systolic = latestSystolic.getMeasurementValue();
        double saturation = latestSaturation.getMeasurementValue();

        if (systolic < LOW_SYSTOLIC_LIMIT && saturation < LOW_SATURATION_LIMIT) {
            alerts.add(alertFactory.createAlert(
                    patient.getPatientId(),
                    "Hypotensive Hypoxemia Alert",
                    Math.max(
                            latestSystolic.getTimestamp(),
                            latestSaturation.getTimestamp())));
        }

        return alerts;
    }
}