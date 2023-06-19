package de.uol.swp.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConfigurationTest {

    @Test
    void getDefaultPort() {
        int expectedPort = 8899;
        int actualPort = Configuration.getDefaultPort();
        assertEquals(expectedPort, actualPort);
    }
}
