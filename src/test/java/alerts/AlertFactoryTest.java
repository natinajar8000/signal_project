package alerts;

import org.junit.jupiter.api.Test;

import com.alerts.factory.AlertFactory;
import com.alerts.factory.BloodOxygenAlertFactory;
import com.alerts.factory.BloodPressureAlertFactory;
import com.alerts.factory.ECGAlertFactory;
import com.alerts.factory.HypotensiveHypoxemiaAlertFactory;
import com.alerts.factory.TriggeredAlertFactory;
import com.alerts.alert.Alert;
import com.alerts.alert.BasicAlert;

import static org.junit.jupiter.api.Assertions.*;

class AlertFactoryTest {

    @Test
    void testBloodOxygenFactory() {
        AlertFactory factory = new BloodOxygenAlertFactory();

        Alert alert = factory.createAlert(1, "test", 1000);

        assertInstanceOf(BasicAlert.class, alert);
    }

    @Test
    void testBloodPressureFactory() {
        AlertFactory factory = new BloodPressureAlertFactory();

        Alert alert = factory.createAlert(1, "test", 1000);

        assertInstanceOf(BasicAlert.class, alert);
    }

    @Test
    void testECGFactory() {
        AlertFactory factory = new ECGAlertFactory();

        Alert alert = factory.createAlert(1, "test", 1000);

        assertInstanceOf(BasicAlert.class, alert);
    }

    @Test
    void testHypotensiveHypoxemiaFactory() {
        AlertFactory factory = new HypotensiveHypoxemiaAlertFactory();

        Alert alert = factory.createAlert(1, "test", 1000);

        assertInstanceOf(BasicAlert.class, alert);
    }

    @Test
    void testTriggeredAlertFactory() {
        AlertFactory factory = new TriggeredAlertFactory();

        Alert alert = factory.createAlert(1, "test", 1000);

        assertInstanceOf(BasicAlert.class, alert);
    }
}