package alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.BloodOxygenAlertStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BloodOxygenAlertStrategyTest {

    private Patient patient() {
        return new Patient(1);
    }

    private PatientRecord record(double value, String type, long timestamp) {
        return new PatientRecord(1, value, type, timestamp);
    }

    @Test
    void testLowSaturationAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(95, "Saturation", 1000),
                record(91, "Saturation", 2000)
        ));

        BloodOxygenAlertStrategy strategy = new BloodOxygenAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertEquals(1, alerts.size());
    }

    @Test
    void testRapidDropAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(98, "Saturation", 1000),
                record(93, "Saturation", 1000 + 5 * 60 * 1000)
        ));

        BloodOxygenAlertStrategy strategy = new BloodOxygenAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertEquals(1, alerts.size());
    }

    @Test
    void testLowSaturationAndRapidDropAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(98, "Saturation", 1000),
                record(90, "Saturation", 1000 + 5 * 60 * 1000)
        ));

        BloodOxygenAlertStrategy strategy = new BloodOxygenAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertEquals(2, alerts.size());
    }

    @Test
    void testNoBloodOxygenAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(96, "Saturation", 1000),
                record(95, "Saturation", 2000)
        ));

        BloodOxygenAlertStrategy strategy = new BloodOxygenAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertTrue(alerts.isEmpty());
    }
}