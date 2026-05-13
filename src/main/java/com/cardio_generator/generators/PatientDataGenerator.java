package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface for all patient data generators
 * defines the contract for simulating different types of health data
 */
public interface PatientDataGenerator {

    /**
     * Generates data for specific patient and sends it to the output
     * 
     * @param patientId      unique patient ID
     * @param outputStrategy method used to send or store generated data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
