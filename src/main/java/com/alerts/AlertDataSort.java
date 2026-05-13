package com.alerts;

import com.data_management.Patient;
import com.data_management.PatientRecord;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class AlertDataSort {

    private AlertDataSort() {}

    public static List<PatientRecord> getRecordsByType(Patient patient, String recordType) {
        List<PatientRecord> allRecords = patient.getRecords(0, System.currentTimeMillis());
        List<PatientRecord> matchingRecords = new ArrayList<>();

        for (PatientRecord record : allRecords) {
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