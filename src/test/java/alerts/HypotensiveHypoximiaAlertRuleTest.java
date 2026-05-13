package alerts;

import com.alerts.Alert;
import com.alerts.HypotensiveHypoxemiaAlertRule;
import com.data_management.Patient;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HypotensiveHypoxemiaAlertRuleTest {

    @Test
    void triggersWhenBloodPressureAndSaturationAreBothLow() {
        Patient patient = new Patient(1);
        patient.addRecord(85, "SystolicPressure", 1000);
        patient.addRecord(91, "Saturation", 1000);

        HypotensiveHypoxemiaAlertRule rule = new HypotensiveHypoxemiaAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 2000));

        assertFalse(alerts.isEmpty());
        assertEquals("Hypotensive Hypoxemia Alert", alerts.get(0).getCondition());
    }

    @Test
    void doesNotTriggerWhenOnlySaturationIsLow() {
        Patient patient = new Patient(1);
        patient.addRecord(100, "SystolicPressure", 1000);
        patient.addRecord(91, "Saturation", 1000);

        HypotensiveHypoxemiaAlertRule rule = new HypotensiveHypoxemiaAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 2000));

        assertTrue(alerts.isEmpty());
    }
}