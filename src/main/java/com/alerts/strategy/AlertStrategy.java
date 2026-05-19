package com.alerts.strategy;

import com.alerts.alert.Alert;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;

public interface AlertStrategy {
    List<Alert> check(Patient patient, List<PatientRecord> patientRecords);
}