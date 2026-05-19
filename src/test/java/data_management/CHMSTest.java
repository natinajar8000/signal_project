package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.alerts.AlertGenerator; 
import com.alerts.alert.Alert;
import com.alerts.alert.BasicAlert;
import com.alerts.decorator.PriorityAlertDecorator;
import com.alerts.decorator.RepeatedAlertDecorator;
import com.alerts.utilities.AlertDataSort;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.List;
import java.util.ArrayList;

// import com.alerts.*;
// import java.util.List;
// import java.util.ArrayList;

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

    // @Test
    // void testMockAlertRule() {
    // AlertRule mockRule = (patient, records) -> {
    // List<Alert> alerts = new ArrayList<>();
    // if (!records.isEmpty()) {
    // alerts.add(new BasicAlert(patient.getPatientId(), "Test Condition",
    // System.currentTimeMillis()));
    // }
    // return alerts;
    // };

    // Patient patient = new Patient(1);
    // List<PatientRecord> records = new ArrayList<>();
    // records.add(new PatientRecord(1, 100.0, "TestType", 1000L));

    // List<Alert> triggeredAlerts = mockRule.check(patient, records);

    // assertEquals(1, triggeredAlerts.size());
    // assertEquals("Test Condition", triggeredAlerts.get(0).getCondition());
    // }

    @Test
    void testAlertGeneratorWithEmptyData() {
        DataStorage storage = DataStorage.getInstance();
        AlertGenerator generator = new AlertGenerator(storage);
        Patient patient = new Patient(1);

        assertDoesNotThrow(() -> generator.evaluateData(patient));
    }

    @Test
    void testDataStorageSingleton() {
        DataStorage instance1 = DataStorage.getInstance();
        DataStorage instance2 = DataStorage.getInstance();

        assertSame(instance1, instance2);
    }

    @Test
    void testAlertDecorators() {
        Alert alert = new BasicAlert(1, "Low Oxygen", 1000L);

        Alert priorityAlert = new PriorityAlertDecorator(alert, "Urgent");
        Alert fullyDecoratedAlert = new RepeatedAlertDecorator(priorityAlert, 10);

        assertEquals(1, fullyDecoratedAlert.getPatientId());
        assertEquals("[URGENT] Low Oxygen [repeated every 10s]", fullyDecoratedAlert.getCondition());
    }

    @Test
    void testBasicAlertCreationAndGetters() {
        int expectedPatientId = 99;
        String expectedCondition = "High Heart Rate";
        long expectedTimestamp = 1715875200000L;

        Alert alert = new BasicAlert(expectedPatientId, expectedCondition, expectedTimestamp);

        assertEquals(expectedPatientId, alert.getPatientId());
        assertEquals(expectedCondition, alert.getCondition());
        assertEquals(expectedTimestamp, alert.getTimestamp());
    }

    @Test
    void testPriorityAlertDecoratorModifiesCondition() {
        Alert basicAlert = new BasicAlert(1, "SPO2 Drop", 1000L);
        Alert priorityAlert = new PriorityAlertDecorator(basicAlert, "Urgent");

        assertEquals("[URGENT] SPO2 Drop", priorityAlert.getCondition());
    }

    @Test
    void testPriorityAlertDecoratorPreservesBasicData() {
        int patientId = 42;
        long timestamp = 5000L;
        Alert basicAlert = new BasicAlert(patientId, "ECG Anomaly", timestamp);
        Alert priorityAlert = new PriorityAlertDecorator(basicAlert, "Critical");

        assertEquals(patientId, priorityAlert.getPatientId());
        assertEquals(timestamp, priorityAlert.getTimestamp());
    }

    @Test
    void testPriorityAlertDecoratorHandlesCaseInsensitivity() {
        Alert basicAlert = new BasicAlert(5, "Fever", 2000L);
        Alert priorityAlert = new PriorityAlertDecorator(basicAlert, "warning");

        assertEquals("[WARNING] Fever", priorityAlert.getCondition());
    }

    @Test
    void testMultiplePriorityDecoratorsStacking() {
        Alert alert = new BasicAlert(12, "Blood Pressure Spike", 3000L);

        Alert warningAlert = new PriorityAlertDecorator(alert, "Warning");
        Alert doubleDecoratedAlert = new PriorityAlertDecorator(warningAlert, "Severe");

        assertEquals("[SEVERE] [WARNING] Blood Pressure Spike", doubleDecoratedAlert.getCondition());
    }
}
