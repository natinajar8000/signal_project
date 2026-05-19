package alerts;

import com.alerts.utilities.AlertDataSort;
import com.data_management.PatientRecord;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AlertDataSortTest {

    private PatientRecord record(double value,String type, long timestamp) {
        return new PatientRecord(1, value, type, timestamp);
    }

    @Test
    void testGetRecordsByType() {
        PatientRecord later = record(95, "Saturation", 3000);
        PatientRecord ecg = record(1.2, "ECG", 1000);
        PatientRecord earlier = record(90, "Saturation", 1000);

        List<PatientRecord> result = AlertDataSort.getRecordsByType(
                List.of(later, ecg, earlier),
                "Saturation"
        );

        assertEquals(2, result.size());
        assertSame(earlier, result.get(0));
        assertSame(later, result.get(1));
    }

    @Test
    void testGetLatestRecord() {
        PatientRecord oldRecord = record(96, "Saturation", 1000);
        PatientRecord newRecord = record(91, "Saturation", 3000);

        List<PatientRecord> records = new ArrayList<>(
            List.of(oldRecord, newRecord)
        );
        PatientRecord latest = AlertDataSort.getLatestRecord(records);

        
        assertSame(newRecord, latest);
    }

    @Test
    void testGetLatestRecordEmptyList() {
        PatientRecord latest = AlertDataSort.getLatestRecord(List.of());

        assertNull(latest);
    }
}