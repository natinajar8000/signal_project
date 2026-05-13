package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

// Changed the name of the class to upper-case 
public class FileOutputStrategy implements OutputStrategy {

    // Changed BaseDirectory to baseDirectory
    // Made the field final
    private final String baseDirectory;

    // Changed file_map to fileMap
    // Made the field private
    private final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

    // Changed the name to upper-case
    /**
     * Creates a new file output strategy using the given base directory.
     * 
     * @param baseDirectory the base directory where the output files will be created
     */
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs patient data to a text file based on the label.
     * If the directory does not exist, it is created automatically.
     *
     * @param patientId the ID of the patient
     * @param timestamp the time when the data was generated
     * @param label the type of data being written
     * @param data the generated patient data
     */
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }

        // Set the FilePath variable
        // Changed FilePath to filepath
        // Avoided 100+ characters in one line
        String filePath = 
            fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        // Avoided 100+ characters in one line
        try (PrintWriter out =
            new PrintWriter(
                Files.newBufferedWriter(
                    Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf(
                "Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n",
                 patientId, timestamp, label, data);
        } catch (IOException e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}