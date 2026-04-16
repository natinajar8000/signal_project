package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

// FIX: This class has a name error, changed to FileOutputStrategy to match the class name in the file
public class FileOutputStrategy implements OutputStrategy {
// FIX: The name of this variable started with upper case, changed it to lower case to follow Google Java Style Guide
    private String baseDirectory;
// FIX: The name of this variable used an underscore, fixed it to match Google Java Style Guide and made it private to ensure encapsulation
    private final ConcurrentHashMap<String, String> fileMap = new ConcurrentHashMap<>();

// FIX: This constructor has a name error, changed to FileOutputStrategy to match the class name in the file 
    public FileOutputStrategy(String baseDirectory) {

        this.baseDirectory = baseDirectory;
    }
// FIX: This method had unnecessary comments and a name error, removed the comments and changed the name "file_map" to "fileMap"
    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        
        String FilePath = fileMap.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

    // FIX: Changed layout of out.printf to make it <100 chars per line
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(FilePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf(
                "Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", 
                patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + FilePath + ": " + e.getMessage());
        }
    }
}