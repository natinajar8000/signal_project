package alerts;

import com.alerts.Alert;
import com.alerts.ECGAlertRule;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ECGAlertRuleTest {

    @Test
    void doesNotTriggerWhenThereAreFewerThanTenRecords() {
        Patient patient = new Patient(1);

        for (int i = 0; i < 9; i++) {
            patient.addRecord(0.2, "ECG", i);
        }

        ECGAlertRule rule = new ECGAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 20));

        assertTrue(alerts.isEmpty());
    }

    @Test
    void triggersWhenLatestPeakIsFarAboveAverage() {
        Patient patient = new Patient(1);

        for (int i = 0; i < 9; i++) {
            patient.addRecord(0.1, "ECG", i);
        }

        patient.addRecord(2.0, "ECG", 10);

        ECGAlertRule rule = new ECGAlertRule();

        List<Alert> alerts = rule.check(patient, patient.getRecords(0, 20));

        assertFalse(alerts.isEmpty());
        assertTrue(alerts.get(0).getCondition().contains("Abnormal ECG peak"));
    }
}