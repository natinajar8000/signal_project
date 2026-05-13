package alerts;

import com.alerts.Alert;
import com.alerts.BloodPressureAlertRule;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BloodPressureAlertRuleTest {

    @Test
    void triggersHighSystolicAlert() {
        Patient patient = new Patient(1);
        patient.addRecord(181, "SystolicPressure", 1000);

        BloodPressureAlertRule rule = new BloodPressureAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 2000));

        assertFalse(alerts.isEmpty());
        assertTrue(alerts.get(0).getCondition().contains("Critical systolic"));
    }

    @Test
    void triggersLowDiastolicAlert() {
        Patient patient = new Patient(1);
        patient.addRecord(59, "DiastolicPressure", 1000);

        BloodPressureAlertRule rule = new BloodPressureAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 2000));

        assertFalse(alerts.isEmpty());
        assertTrue(alerts.get(0).getCondition().contains("Critical diastolic"));
    }

    @Test
    void triggersIncreasingTrendAlert() {
        Patient patient = new Patient(1);
        patient.addRecord(100, "SystolicPressure", 1000);
        patient.addRecord(112, "SystolicPressure", 2000);
        patient.addRecord(125, "SystolicPressure", 3000);

        BloodPressureAlertRule rule = new BloodPressureAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 4000));

        assertTrue(alerts.stream()
                .anyMatch(alert -> alert.getCondition().contains("increasing trend")));
    }

    @Test
    void triggersDecreasingTrendAlert() {
        Patient patient = new Patient(1);
        patient.addRecord(140, "SystolicPressure", 1000);
        patient.addRecord(128, "SystolicPressure", 2000);
        patient.addRecord(115, "SystolicPressure", 3000);

        BloodPressureAlertRule rule = new BloodPressureAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 4000));

        assertTrue(alerts.stream()
                .anyMatch(alert -> alert.getCondition().contains("decreasing trend")));
    }

    @Test
    void doesNotTriggerTrendWhenChangeIsOnlyTen() {
        Patient patient = new Patient(1);
        patient.addRecord(100, "SystolicPressure", 1000);
        patient.addRecord(110, "SystolicPressure", 2000);
        patient.addRecord(120, "SystolicPressure", 3000);

        BloodPressureAlertRule rule = new BloodPressureAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 4000));

        assertTrue(alerts.isEmpty());
    }
}