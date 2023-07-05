package de.uol.swp.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ConfigurationTest {

    /**
     * Tests the getDefaultHost method
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @Test
    public void testGetDefaultPort() {
        int expectedPort = 8899;
        int actualPort = Configuration.getDefaultPort();
        assertEquals(expectedPort, actualPort);
    }
}
