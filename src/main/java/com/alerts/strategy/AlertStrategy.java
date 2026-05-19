package com.alerts.strategy;

import com.alerts.alert.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

/**
 * Interface for alert strategies
 */
public interface AlertStrategy {
    /**
     * Checks the patient's data and generates alerts
     * @param patient the patient to check
     * @param patientRecords the list of patient records to analyze
     * @return a list of generated alerts
     */
    List<Alert> check(Patient patient, List<PatientRecord> patientRecords);
}