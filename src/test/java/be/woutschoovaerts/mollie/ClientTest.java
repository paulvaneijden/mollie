package be.woutschoovaerts.mollie;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    void constructor() {
        Client client = new Client("apiKey");

        assertEquals("apiKey", client.getConfig().getApiKey());
    }

    @Test
    void setAccessToken() {
        Client client = new Client("apiKey");

        client.setAccessToken("access_token");

        assertEquals("access_token", client.getConfig().getAccessToken());
    }

    @Test
    void revokeAccessToken() {
        Client client = new Client("apiKey");

        client.setAccessToken("access_token");

        assertEquals("access_token", client.getConfig().getAccessToken());

        client.revokeAccessToken();

        assertNull(client.getConfig().getAccessToken());
    }

    @Test
    void enableTestMode() {
        Client client = new Client("apiKey");
        client.enableTestMode();

        assertTrue(client.getConfig().isTestMode());
    }

    @Test
    void disableTestMode() {
        Client client = new Client("apiKey");
        client.enableTestMode();
        client.disableTestMode();

        assertFalse(client.getConfig().isTestMode());
    }

    @Test
    void setUserAgentString() {
        Client client = new Client("apiKey");
        client.setUserAgentString("user_agent/version");

        assertEquals(Optional.of("user_agent/version"), client.getConfig().getUserAgentString());
    }

    @Test
    void defaultUserAgentString() {
        Client client = new Client("apiKey");

        assertEquals(Optional.empty(), client.getConfig().getUserAgentString());
    }

    @Test
    void twoClients() {
        Client client1 = new Client("apiKey1");
        Client client2 = new Client("apiKey2");

        assertEquals("apiKey1", client1.getConfig().getApiKey());
        assertEquals("apiKey2", client2.getConfig().getApiKey());
    }

    @Test
    void clientWithProxy() {
        ClientProxy clientProxy = ClientProxy.builder()
                .host("localhost")
                .port(9999)
                .username("wout")
                .password("securityIsKey")
                .build();
        Client client = new Client("apiKey1", clientProxy);

        assertEquals("apiKey1", client.getConfig().getApiKey());
        assertTrue(client.getConfig().getProxy().isPresent());
        assertEquals("localhost", client.getConfig().getProxy().get().getHost());
        assertEquals(9999, client.getConfig().getProxy().get().getPort());
        assertEquals("wout", client.getConfig().getProxy().get().getUsername());
        assertEquals("securityIsKey", client.getConfig().getProxy().get().getPassword());
    }

}