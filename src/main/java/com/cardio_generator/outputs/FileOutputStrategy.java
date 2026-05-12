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
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }
  
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