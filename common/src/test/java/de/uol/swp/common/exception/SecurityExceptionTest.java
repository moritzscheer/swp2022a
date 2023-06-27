package de.uol.swp.common.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecurityExceptionTest {

    protected String message = "Security Exception";

    @Test
    public void testConstructor() {
        SecurityException securityException = new SecurityException(message);
        assertEquals(message, securityException.getMessage());
    }
}
