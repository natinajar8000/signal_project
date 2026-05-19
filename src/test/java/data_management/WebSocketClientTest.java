package data_management;

import static org.junit.jupiter.api.Assertions.*;
import java.net.URI;

import org.junit.jupiter.api.Test;

import com.data_management.DataStorage;
import com.data_management.WebSocketClient;

class WebSocketClientTest {

    @Test
    void testClientStartsDisconnected() throws Exception {
        DataStorage dataStorage = DataStorage.getInstance();

        WebSocketClient client =
                new WebSocketClient(new URI("ws://localhost:6767"), dataStorage);

        assertFalse(client.isConnectedToServer());
    }

    @Test
    void testClientBecomesConnectedWhenOpened() throws Exception {
        DataStorage dataStorage = DataStorage.getInstance();

        WebSocketClient client =
                new WebSocketClient(new URI("ws://localhost:6767"), dataStorage);

        client.onOpen(null);

        assertTrue(client.isConnectedToServer());
    }

    @Test
    void testClientBecomesDisconnectedWhenClosed() throws Exception {
        DataStorage dataStorage = DataStorage.getInstance();

        WebSocketClient client =
                new WebSocketClient(new URI("ws://localhost:6767"), dataStorage);

        client.onOpen(null);
        client.onClose(1000, "closed", false);

        assertFalse(client.isConnectedToServer());
    }

    @Test
    void testClientBecomesDisconnectedWhenErrorHappens() throws Exception {
        DataStorage dataStorage = DataStorage.getInstance();

        WebSocketClient client =
                new WebSocketClient(new URI("ws://localhost:6767"), dataStorage);

        client.onOpen(null);
        client.onError(new RuntimeException("test error"));

        assertFalse(client.isConnectedToServer());
    }

    @Test
    void testNullDataStorageIsNotAllowed() throws Exception {
        assertThrows(
                IllegalArgumentException.class,
                () -> new WebSocketClient(new URI("ws://localhost:6767"), null)
        );
    }

    @Test
    void testBadMessageDoesNotCrashClient() throws Exception {
        DataStorage dataStorage = DataStorage.getInstance();

        WebSocketClient client =
                new WebSocketClient(new URI("ws://localhost:6767"), dataStorage);

        assertDoesNotThrow(() -> client.onMessage("wrong,message"));
    }

    @Test
    void testValidMessageDoesNotCrashClient() throws Exception {
        DataStorage dataStorage = DataStorage.getInstance();

        WebSocketClient client =
                new WebSocketClient(new URI("ws://localhost:6767"), dataStorage);

        assertDoesNotThrow(() -> client.onMessage("1,123456789,heartRate,80.5"));
    }
}