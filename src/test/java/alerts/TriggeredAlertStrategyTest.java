package alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import com.alerts.alert.Alert;
import com.alerts.strategy.TriggeredAlertStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriggeredAlertStrategyTest {

    private Patient patient() {
        return new Patient(1);
    }

    private PatientRecord record(double value, String type, long timestamp) {
        return new PatientRecord(1, value, type, timestamp);
    }

    @Test
    void testTriggeredAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(0, "Alert", 1000),
                record(1, "Alert", 2000)
        ));

        TriggeredAlertStrategy strategy = new TriggeredAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertEquals(1, alerts.size());
    }

    @Test
    void testNoTriggeredAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(1, "Alert", 1000),
                record(0, "Alert", 2000)
        ));

        TriggeredAlertStrategy strategy = new TriggeredAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertTrue(alerts.isEmpty());
    }
}