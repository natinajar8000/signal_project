package com.data_management;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Implementation of DataReader that reads patient data from files in a
 * directory.
 */
public class FileDataReader implements DataReader {

    private final String inputDirectory;

    public FileDataReader(String inputDirectory) {
        this.inputDirectory = inputDirectory;
    }

    @Override
    public void connect(String serverUri, DataStorage dataStorage) throws IOException {
        Path path = Paths.get(inputDirectory);

        // Walk through the directory to find all .txt files
        try (Stream<Path> paths = Files.walk(path)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".txt"))
                    .forEach(p -> parseFile(p, dataStorage));
        }
    }

    @Override
    public void disconnect() throws Exception {
    }

    /**
     * Parses a single file and adds records to data storage.
     * 
     * @param file        Path to the file.
     * @param dataStorage Target storage.
     */
    private void parseFile(Path file, DataStorage dataStorage) {
        try (BufferedReader reader = Files.newBufferedReader(file)) {
            String line;
            while ((line = reader.readLine()) != null) {
                parseLineAndStore(line, dataStorage);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + file + " - " + e.getMessage());
        }
    }

    /**
     * Parses a single line from the file and adds it to the storage.
     * Format: Patient ID: 1, Timestamp: 1715600000, Label: ECG, Data: 75.0
     * 
     * @param line        Raw line from the text file.
     * @param dataStorage Target storage.
     */
    private void parseLineAndStore(String line, DataStorage dataStorage) {
        try {
            // Simple parsing logic based on the format used in FileOutputStrategy
            String[] parts = line.split(", ");
            int patientId = Integer.parseInt(parts[0].split(": ")[1]);
            long timestamp = Long.parseLong(parts[1].split(": ")[1]);
            String label = parts[2].split(": ")[1];
            double data = Double.parseDouble(parts[3].split(": ")[1]);

            dataStorage.addPatientData(patientId, data, label, timestamp);
        } catch (Exception e) {
            System.err.println("Failed to parse line: " + line);
        }
    }
}
