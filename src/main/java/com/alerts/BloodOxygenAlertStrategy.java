package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BloodOxygenAlertStrategy implements AlertStrategy {

    private static final double LOW_SATURATION_LIMIT = 92;
    private static final double RAPID_DROP_LIMIT = 5;
    private static final long TEN_MINUTES = 10 * 60 * 1000L;

    private final AlertFactory alertFactory;

    public BloodOxygenAlertStrategy() {
        this.alertFactory = new BloodOxygenAlertFactory();
    }

    @Override
    public List<Alert> check(Patient patient, List<PatientRecord> patientRecords) {
        List<Alert> alerts = new ArrayList<>();

        List<PatientRecord> saturationRecords =
                AlertDataSort.getRecordsByType(patientRecords, "Saturation");

        PatientRecord latestSaturation = AlertDataSort.getLatestRecord(saturationRecords);

        if (latestSaturation != null
                && latestSaturation.getMeasurementValue() < LOW_SATURATION_LIMIT) {

            alerts.add(alertFactory.createAlert(
                    patient.getPatientId(),
                    "Low blood oxygen saturation: "
                            + latestSaturation.getMeasurementValue() + "%",
                    latestSaturation.getTimestamp()));
        }

        if (hasRapidDropWithinTenMinutes(saturationRecords)) {
            alerts.add(alertFactory.createAlert(
                    patient.getPatientId(),
                    "Rapid oxygen saturation drop detected",
                    System.currentTimeMillis()));
        }

        return alerts;
    }

    private boolean hasRapidDropWithinTenMinutes(List<PatientRecord> records) {
        if (records.size() < 2) {
            return false;
        }

        records.sort(Comparator.comparingLong(PatientRecord::getTimestamp));

        for (int i = 0; i < records.size(); i++) {
            PatientRecord earlier = records.get(i);

            for (int j = i + 1; j < records.size(); j++) {
                PatientRecord later = records.get(j);

                long timeDifference = later.getTimestamp() - earlier.getTimestamp();
                double drop = earlier.getMeasurementValue() - later.getMeasurementValue();

                if (timeDifference <= TEN_MINUTES && drop >= RAPID_DROP_LIMIT) {
                    return true;
                }
            }
        }

        return false;
    }
}