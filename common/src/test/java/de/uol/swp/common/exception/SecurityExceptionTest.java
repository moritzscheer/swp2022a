package de.uol.swp.common.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SecurityExceptionTest {

    protected String message = "Security Exception";

    /**
     * Tests the constructor.
     *
     * @author WKempel
     * @result The constructor should work without throwing an exception
     * @see de.uol.swp.common.exception.SecurityException
     * @since 2023-06-27
     */
    @Test
    public void testConstructor() {
        SecurityException securityException = new SecurityException(message);
        assertEquals(message, securityException.getMessage());
    }
}
