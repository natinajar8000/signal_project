package com;

import com.data_management.DataStorage;
import com.cardio_generator.HealthDataSimulator;

// Main class of the application
public class Main {

    /**
     * The main entry point of the application
     * Opens the simulator or data storage
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            if (args.length > 0 && args[0].equalsIgnoreCase("DataStorage")) {
                DataStorage.main(new String[]{});
            } else {
                HealthDataSimulator.start(new String[]{});
            }
        } catch (Exception e) {
            System.err.println("Error while starting the application:");
            e.printStackTrace();
        }
    }
}