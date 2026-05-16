package alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.ECGAlertStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ECGAlertStrategyTest {

    private Patient patient() {
        return new Patient(1);
    }

    private PatientRecord record(double value, String type, long timestamp) {
        return new PatientRecord(1, value, type, timestamp);
    }

    @Test
    void testLessThanTenRecords() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(0.2, "ECG", 1000),
                record(0.3, "ECG", 2000)
        ));

        ECGAlertStrategy strategy = new ECGAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertTrue(alerts.isEmpty());
    }

    @Test
    void testAbnormalPeakAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(0.2, "ECG", 1000),
                record(0.2, "ECG", 2000),
                record(0.2, "ECG", 3000),
                record(0.2, "ECG", 4000),
                record(0.2, "ECG", 5000),
                record(0.2, "ECG", 6000),
                record(0.2, "ECG", 7000),
                record(0.2, "ECG", 8000),
                record(0.2, "ECG", 9000),
                record(3.0, "ECG", 10000)
        ));

        ECGAlertStrategy strategy = new ECGAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertEquals(1, alerts.size());
    }

    @Test
    void testNoECGAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(0.2, "ECG", 1000),
                record(0.2, "ECG", 2000),
                record(0.2, "ECG", 3000),
                record(0.2, "ECG", 4000),
                record(0.2, "ECG", 5000),
                record(0.2, "ECG", 6000),
                record(0.2, "ECG", 7000),
                record(0.2, "ECG", 8000),
                record(0.2, "ECG", 9000),
                record(0.3, "ECG", 10000)
        ));

        ECGAlertStrategy strategy = new ECGAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertTrue(alerts.isEmpty());
    }
}