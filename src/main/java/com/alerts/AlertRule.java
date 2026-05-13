package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import java.util.List;

public interface AlertRule {
    List<Alert> check(Patient patient, List<PatientRecord> patientRecords);
}
