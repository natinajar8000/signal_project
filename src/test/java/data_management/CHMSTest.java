package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.alerts.*; // Tu są Alert, AlertRule, AlertGenerator, AlertDataSort
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;
import java.util.ArrayList;

import com.alerts.*;
import java.util.List;
import java.util.ArrayList;

class CHMSTest {

    @Test
    void testPatientGetRecords() {
        Patient patient = new Patient(10);
        patient.addRecord(80.0, "HeartRate", 1000L);
        patient.addRecord(90.0, "HeartRate", 2000L);
        patient.addRecord(100.0, "HeartRate", 3000L);

        List<PatientRecord> records = patient.getRecords(1500L, 2500L);

        assertEquals(1, records.size());
        assertEquals(90.0, records.get(0).getMeasurementValue());
    }

    @Test
    void testAlertDataSort() {
        List<PatientRecord> records = new ArrayList<>();
        records.add(new PatientRecord(1, 120.0, "Systolic", 2000L));
        records.add(new PatientRecord(1, 80.0, "Diastolic", 1000L));

        List<PatientRecord> sorted = AlertDataSort.getRecordsByType(records, "Systolic");

        assertEquals(1, sorted.size());
        assertEquals(2000L, sorted.get(0).getTimestamp());
    }

    @Test
    void testMockAlertRule() {
        AlertRule mockRule = (patient, records) -> {
            List<Alert> alerts = new ArrayList<>();
            if (!records.isEmpty()) {
                alerts.add(new Alert(patient.getPatientId(), "Test Condition", System.currentTimeMillis()));
            }
            return alerts;
        };

        Patient patient = new Patient(1);
        List<PatientRecord> records = new ArrayList<>();
        records.add(new PatientRecord(1, 100.0, "TestType", 1000L));

        List<Alert> triggeredAlerts = mockRule.check(patient, records);

        assertEquals(1, triggeredAlerts.size());
        assertEquals("Test Condition", triggeredAlerts.get(0).getCondition());
    }

    @Test
    void testAlertGeneratorWithEmptyData() {
        DataStorage storage = new DataStorage();
        AlertGenerator generator = new AlertGenerator(storage);
        Patient patient = new Patient(1);

        assertDoesNotThrow(() -> generator.evaluateData(patient));
    }

}
