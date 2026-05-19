package com.alerts.strategy;

import com.alerts.alert.Alert;
import com.alerts.factory.AlertFactory;
import com.alerts.factory.HypotensiveHypoxemiaAlertFactory;
import com.alerts.utilities.AlertDataSort;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.List;

public class HypotensiveHypoxemiaAlertStrategy implements AlertStrategy {

    private static final double LOW_SYSTOLIC_LIMIT = 90;
    private static final double LOW_SATURATION_LIMIT = 92;

    private final AlertFactory alertFactory;

    // Constructor that initializes the alert factory
    public HypotensiveHypoxemiaAlertStrategy() {
        this.alertFactory = new HypotensiveHypoxemiaAlertFactory();
    }

    /**
     * Checks the patient's blood pressure and oxygen saturation data to generate a hypotensive hypoxemia alert
     * if the systolic pressure and oxygen saturation are both below their thresholds
     * @param patient the patient to check
     * @param patientRecords the list of patient records to analyze
     * @return a list of generated alerts 
     */
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