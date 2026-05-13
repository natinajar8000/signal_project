package alerts;

import com.alerts.Alert;
import com.alerts.BloodSaturationAlertRule;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BloodSaturationAlertRuleTest {

    @Test
    void triggersLowSaturationAlert() {
        Patient patient = new Patient(1);
        patient.addRecord(91, "Saturation", 1000);

        BloodSaturationAlertRule rule = new BloodSaturationAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 2000));

        assertFalse(alerts.isEmpty());
        assertTrue(alerts.get(0).getCondition().contains("Low blood oxygen saturation"));
    }

    @Test
    void triggersRapidDropWithinTenMinutes() {
        Patient patient = new Patient(1);
        patient.addRecord(98, "Saturation", 1000);
        patient.addRecord(93, "Saturation", 1000 + 5 * 60 * 1000);

        BloodSaturationAlertRule rule = new BloodSaturationAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 400000));

        assertTrue(alerts.stream()
                .anyMatch(alert -> alert.getCondition().contains("Rapid oxygen saturation drop")));
    }

    @Test
    void doesNotTriggerRapidDropOutsideTenMinutes() {
        Patient patient = new Patient(1);
        patient.addRecord(98, "Saturation", 1000);
        patient.addRecord(93, "Saturation", 1000 + 11 * 60 * 1000);

        BloodSaturationAlertRule rule = new BloodSaturationAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 800000));

        assertTrue(alerts.stream()
                .noneMatch(alert -> alert.getCondition().contains("Rapid oxygen saturation drop")));
    }
}