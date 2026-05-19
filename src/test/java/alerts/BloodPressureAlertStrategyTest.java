package alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import com.alerts.alert.Alert;
import com.alerts.strategy.BloodPressureAlertStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BloodPressureAlertStrategyTest {

    private Patient patient() {
        return new Patient(1);
    }

    private PatientRecord record( double value, String type, long timestamp) {
        return new PatientRecord(1, value, type, timestamp);
    }

    @Test
    void testHighSystolicAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(181, "SystolicPressure", 1000),
                record(80, "DiastolicPressure", 1000)
        ));

        BloodPressureAlertStrategy strategy = new BloodPressureAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertEquals(1, alerts.size());
    }

    @Test
    void testLowDiastolicAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(120, "SystolicPressure", 1000),
                record(55, "DiastolicPressure", 1000)
        ));

        BloodPressureAlertStrategy strategy = new BloodPressureAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertEquals(1, alerts.size());
    }

    @Test
    void testIncreasingTrendAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(100, "SystolicPressure", 1000),
                record(115, "SystolicPressure", 2000),
                record(130, "SystolicPressure", 3000)
        ));

        BloodPressureAlertStrategy strategy = new BloodPressureAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertEquals(1, alerts.size());
    }

    @Test
    void testDecreasingTrendAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(140, "SystolicPressure", 1000),
                record(125, "SystolicPressure", 2000),
                record(110, "SystolicPressure", 3000)
        ));

        BloodPressureAlertStrategy strategy = new BloodPressureAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertEquals(1, alerts.size());
    }

    @Test
    void testNoBloodPressureAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(120, "SystolicPressure", 1000),
                record(80, "DiastolicPressure", 1000)
        ));

        BloodPressureAlertStrategy strategy = new BloodPressureAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertTrue(alerts.isEmpty());
    }
}