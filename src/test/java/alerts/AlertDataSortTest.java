package alerts;

import com.alerts.AlertDataSort;
import com.data_management.PatientRecord;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AlertDataSortTest {

    @Test
    void getRecordsByTypeReturnsOnlyMatchingRecordType() {
        List<PatientRecord> records = new ArrayList<>();

        records.add(new PatientRecord(1, 120, "SystolicPressure", 1000));
        records.add(new PatientRecord(1, 80, "DiastolicPressure", 2000));
        records.add(new PatientRecord(1, 130, "SystolicPressure", 3000));

        List<PatientRecord> systolicRecords =
                AlertDataSort.getRecordsByType(records, "SystolicPressure");

        assertEquals(2, systolicRecords.size());
        assertEquals("SystolicPressure", systolicRecords.get(0).getRecordType());
        assertEquals("SystolicPressure", systolicRecords.get(1).getRecordType());
    }

    @Test
    void getRecordsByTypeSortsRecordsByTimestamp() {
        List<PatientRecord> records = new ArrayList<>();

        records.add(new PatientRecord(1, 130, "SystolicPressure", 3000));
        records.add(new PatientRecord(1, 120, "SystolicPressure", 1000));
        records.add(new PatientRecord(1, 125, "SystolicPressure", 2000));

        List<PatientRecord> sortedRecords =
                AlertDataSort.getRecordsByType(records, "SystolicPressure");

        assertEquals(1000, sortedRecords.get(0).getTimestamp());
        assertEquals(2000, sortedRecords.get(1).getTimestamp());
        assertEquals(3000, sortedRecords.get(2).getTimestamp());
    }

    @Test
    void getRecordsByTypeReturnsEmptyListWhenNoRecordsMatch() {
        List<PatientRecord> records = new ArrayList<>();

        records.add(new PatientRecord(1, 98, "Saturation", 1000));

        List<PatientRecord> ecgRecords =
                AlertDataSort.getRecordsByType(records, "ECG");

        assertTrue(ecgRecords.isEmpty());
    }

    @Test
    void getLatestRecordReturnsMostRecentRecord() {
        List<PatientRecord> records = new ArrayList<>();

        records.add(new PatientRecord(1, 120, "SystolicPressure", 1000));
        records.add(new PatientRecord(1, 130, "SystolicPressure", 3000));
        records.add(new PatientRecord(1, 125, "SystolicPressure", 2000));

        PatientRecord latest = AlertDataSort.getLatestRecord(records);

        assertNotNull(latest);
        assertEquals(3000, latest.getTimestamp());
        assertEquals(130, latest.getMeasurementValue());
    }

    @Test
    void getLatestRecordReturnsNullWhenListIsEmpty() {
        List<PatientRecord> records = new ArrayList<>();

        PatientRecord latest = AlertDataSort.getLatestRecord(records);

        assertNull(latest);
    }
}