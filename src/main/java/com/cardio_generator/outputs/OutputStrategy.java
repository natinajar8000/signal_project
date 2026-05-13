package com.cardio_generator.outputs;

/**
 * Interface defining how the generated health data should be exported
 */
public interface OutputStrategy {

    /**
     * Sends data to specific output destination
     * 
     * @param patientId unique ID of the patient
     * @param timestamp time of the data generation in milliseconds
     * @param label     label type of data being sent
     * @param data      the actual generated value or status
     */
    void output(int patientId, long timestamp, String label, String data);
}
