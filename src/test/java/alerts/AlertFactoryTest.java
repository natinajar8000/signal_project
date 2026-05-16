package alerts;

import org.junit.jupiter.api.Test;
import com.alerts.AlertFactory;
import com.alerts.Alert;
import com.alerts.BloodOxygenAlertFactory;
import com.alerts.BloodPressureAlertFactory;
import com.alerts.ECGAlertFactory;
import com.alerts.HypotensiveHypoxemiaAlertFactory;
import com.alerts.TriggeredAlertFactory;
import com.alerts.BasicAlert;

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