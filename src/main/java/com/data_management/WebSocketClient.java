package com.data_management;

import java.net.URI;
import java.net.URISyntaxException;

import org.java_websocket.handshake.ServerHandshake;

/**
 * WebSocket client that recieves real-time patient measurements
 * from the simulator and stores them in DataStorage.
 */
public class WebSocketClient extends org.java_websocket.client.WebSocketClient {

    private static final int EXPECTED_FIELD_COUNT = 4;

    private final DataStorage dataStorage;
    private boolean connected;

    /**
     * Creates a client connected to the WebSocket's server URI.
     *
     * @param uri   WebSocket server URI
     * @param dataStorage storage used to save parsed patient data
     */
    public WebSocketClient(URI uri, DataStorage dataStorage) {
        super(uri);

        if (dataStorage == null) {
            throw new IllegalArgumentException("DataStorage cannot be null");
        }

        this.dataStorage = dataStorage;
        this.connected = false;
    }

    /**
     *  Constructor that creates the client from a String URI.
     *
     * @param uri   WebSocket server URI as text
     * @param dataStorage storage used to save parsed patient data
     * @throws URISyntaxException if the URI is not valid
     */
    public WebSocketClient(String uri, DataStorage dataStorage) throws URISyntaxException {
        this(new URI(uri), dataStorage);
    }

    @Override
    public void onOpen(ServerHandshake handshake) {
        connected = true;
        System.out.println("Connected to WebSocket server: " + getURI());
    }

    @Override
    public void onMessage(String message) {
        try {
            parseAndStore(message);
        } catch (IllegalArgumentException e) {
            // Catches an error in case there is a problem with the messaage
            System.err.println("Invalid WebSocket message skipped: " + message);
            System.err.println("Reason: " + e.getMessage());
        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        connected = false;
        System.err.println("WebSocket connection closed. Code: " + code
                + ", reason: " + reason + ", remote: " + remote);
    }

    @Override
    public void onError(Exception e) {
        connected = false;
        System.err.println("WebSocket error: " + e.getMessage());
    }

    /**
     * Parses a message and stores it in DataStorage.
     * 
     * @param message raw WebSocket message
     */
    void parseAndStore(String message) {
        PatientMeasurement measurement = parseMessage(message);
        dataStorage.addPatientData(
                measurement.patientId,
                measurement.measurementValue,
                measurement.recordType,
                measurement.timestamp);
    }

    /**
     * Converts a WebSocket message into an actual patient measurement.
     *
     * @param message raw message in patientId,timestamp,label,data format
     * @return parsed patient measurement
     */
    static PatientMeasurement parseMessage(String message) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message is empty");
        }

        String[] parts = message.split(",", -1);
        if (parts.length != EXPECTED_FIELD_COUNT) {
            throw new IllegalArgumentException(
                    "Expected parameters: patientId,timestamp,label,data");
        }

        try {
            int patientId = Integer.parseInt(parts[0].trim());
            long timestamp = Long.parseLong(parts[1].trim());
            String label = parts[2].trim();
            double data = Double.parseDouble(parts[3].trim());

            validateMeasurement(patientId, timestamp, label, data);
            return new PatientMeasurement(patientId, timestamp, label, data);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Patient ID,timestamp, and data must be numeric", e);
        }
    }

    /**
     * Validates the measurement values and throws an exception in case of an error
     * @param patientId
     * @param timestamp
     * @param label
     * @param data
     */
    private static void validateMeasurement(
            int patientId,
            long timestamp,
            String label,
            double data) {

        if (patientId <= 0) {
            throw new IllegalArgumentException("Patient ID must be positive");
        }

        if (timestamp <= 0) {
            throw new IllegalArgumentException("Timestamp must be positive");
        }

        if (label == null || label.isBlank()) {
            throw new IllegalArgumentException("Label cannot be empty");
        }

        if (Double.isNaN(data) || Double.isInfinite(data)) {
            throw new IllegalArgumentException("Measurement value must be a valid number");
        }
    }

    public boolean isConnectedToServer() {
        return connected;
    }

    /**
     * Helper class for representing a patient measurement that
     * was parsed from a WebSocket message
     */
    static class PatientMeasurement {
        final int patientId;
        final long timestamp;
        final String recordType;
        final double measurementValue;

        PatientMeasurement(int patientId, long timestamp, String recordType, double measurementValue) {
            this.patientId = patientId;
            this.timestamp = timestamp;
            this.recordType = recordType;
            this.measurementValue = measurementValue;
        }
    }
}