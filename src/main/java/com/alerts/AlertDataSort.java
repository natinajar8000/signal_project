package com.alerts;

import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class AlertDataSort {

    private AlertDataSort() {
    }

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

    public static PatientRecord getLatestRecord(List<PatientRecord> records) {
        if (records.isEmpty()) {
            return null;
        }

        records.sort(Comparator.comparingLong(PatientRecord::getTimestamp));
        return records.get(records.size() - 1);
    }
}