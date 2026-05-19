package com.data_management;

import java.io.IOException;

public interface DataReader {
    // /**
    // * Reads data from a specified source and stores it in the data storage.
    // *
    // * @param dataStorage the storage where data will be stored
    // * @throws IOException if there is an error reading the data
    // */
    // void readData(DataStorage dataStorage) throws IOException;

    /**
     * Connects to websocket server and starts streaming data
     * 
     * @param serverUri the URI of the websocket server
     * 
     * @param storage   the central storage instance where data will be kept
     * 
     * @throws Exception if connection fails
     */
    void connect(String serverUri, DataStorage storage) throws IOException;

    void disconnect() throws IOException;
}
