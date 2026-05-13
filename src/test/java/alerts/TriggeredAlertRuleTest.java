package alerts;

import com.alerts.Alert;
import com.alerts.TriggeredAlertRule;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriggeredAlertRuleTest {

    @Test
    void triggersWhenAlertValueIsOne() {
        Patient patient = new Patient(1);
        patient.addRecord(1, "Alert", 1000);

        TriggeredAlertRule rule = new TriggeredAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 2000));

        assertFalse(alerts.isEmpty());
    }

    @Test
    void doesNotTriggerWhenAlertValueIsZero() {
        Patient patient = new Patient(1);
        patient.addRecord(0, "Alert", 1000);

        TriggeredAlertRule rule = new TriggeredAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 2000));

        assertTrue(alerts.isEmpty());
    }
}