package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

public class AlertGenerator implements PatientDataGenerator {
// FIX: Changed the name of randomGenerator to RANDOM_GENERATOR, since it is a constant, in order to follow Google Java Style Guide
    public static final Random RANDOM_GENERATOR = new Random();
    private boolean[] alertStates; // false = resolved, true = pressed

    public AlertGenerator(int patientCount) {
// FIX: Changed the name of AlertStates to lower case in order to follow Google Java Style Guide
        alertStates = new boolean[patientCount + 1];
    }

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (RANDOM_GENERATOR.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                // FIX: Andjusted the Lambda name to lambda in order to follow Google Java Style Guide
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = RANDOM_GENERATOR.nextDouble() < p;

                if (alertTriggered) {
                    alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println(
                "An error occurred while generating alert data for patient " + patientId);
            e.printStackTrace();
        }
    }
}
