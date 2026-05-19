package alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import com.alerts.alert.Alert;
import com.alerts.strategy.HypotensiveHypoxemiaAlertStrategy;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HypotensiveHypoxemiaAlertStrategyTest {

    private Patient patient() {
        return new Patient(1);
    }

    private PatientRecord record(double value, String type, long timestamp) {
        return new PatientRecord(1, value, type, timestamp);
    }

    @Test
    void testHypotensiveHypoxemiaAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(85, "SystolicPressure", 1000),
                record(90, "Saturation", 2000)
        ));

        HypotensiveHypoxemiaAlertStrategy strategy =
                new HypotensiveHypoxemiaAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertEquals(1, alerts.size());
    }

    @Test
    void testNoHypotensiveHypoxemiaAlert() {
        List<PatientRecord> records = new ArrayList<>(List.of(
                record(85, "SystolicPressure", 1000),
                record(95, "Saturation", 2000)
        ));

        HypotensiveHypoxemiaAlertStrategy strategy =
                new HypotensiveHypoxemiaAlertStrategy();

        List<Alert> alerts = strategy.check(patient(), records);

        assertTrue(alerts.isEmpty());
    }
}