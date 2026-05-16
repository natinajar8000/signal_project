package alerts;

import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class AlertGeneratorTest {

    @Test
    void testEvaluateDataPrintsTriggeredAlert() {
        DataStorage storage = DataStorage.getInstance();

        storage.addPatientData(1, 1, "Alert", 1000);

        Patient patient = new Patient(1);
        AlertGenerator generator = new AlertGenerator(storage);

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOutput = System.out;

        System.setOut(new PrintStream(output));

        try {
            generator.evaluateData(patient);
        } finally {
            System.setOut(originalOutput);
        }

        String printedOutput = output.toString();

        assertTrue(printedOutput.contains("Alert:"));
        assertTrue(printedOutput.contains("Patient: 1"));
        assertTrue(printedOutput.contains("Condition: Alert triggered at:"));
        assertTrue(printedOutput.contains("Time: 1000"));
    }
}