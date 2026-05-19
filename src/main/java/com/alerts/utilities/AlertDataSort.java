package com.alerts.utilities;

import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class AlertDataSort {

    private AlertDataSort() {
    }

    /**
     * Filters the patient records by record type and sorts them by timestamp
     * @param records
     * @param recordType
     * @return
     */
    public static List<PatientRecord> getRecordsByType(
            List<PatientRecord> records,
            String recordType) {

        List<PatientRecord> matchingRecords = new ArrayList<>();

        for (PatientRecord record : records) {
            if (record.getRecordType().equals(recordType)) {
                matchingRecords.add(record);
            }
        }

        matchingRecords.sort(Comparator.comparingLong(PatientRecord::getTimestamp));
        return matchingRecords;
    }

    /**
     * Gets the latest patient record from a list of records
     * @param records 
     * @return the latest patient record or null if the list is empty
     */
    public static PatientRecord getLatestRecord(List<PatientRecord> records) {
        if (records.isEmpty()) {
            return null;
        }

        records.sort(Comparator.comparingLong(PatientRecord::getTimestamp));
        return records.get(records.size() - 1);
    }
}