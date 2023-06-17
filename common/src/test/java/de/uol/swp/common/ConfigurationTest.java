package de.uol.swp.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConfigurationTest {

    @Test
    void getDefaultPort() {
        int expectedPort = 8899;
        int actualPort = Configuration.getDefaultPort();
        assertEquals(expectedPort, actualPort);
    }
}
