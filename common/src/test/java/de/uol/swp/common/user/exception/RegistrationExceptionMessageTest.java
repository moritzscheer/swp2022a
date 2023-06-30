package de.uol.swp.common.user.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationExceptionMessageTest {

    private RegistrationExceptionMessage exceptionMessage;

    @BeforeEach
    void setUp() {
        exceptionMessage = new RegistrationExceptionMessage("Test Exception");
    }

    @Test
    void testToString() {
        assertEquals("RegistrationExceptionMessage Test Exception", exceptionMessage.toString());
    }

    @Test
    void testEquals() {
        RegistrationExceptionMessage message1 = new RegistrationExceptionMessage("Test Exception");
        RegistrationExceptionMessage message2 =
                new RegistrationExceptionMessage("Different Exception");

        // Same object
        assertEquals(message1, message1);
        // Different objects
        assertNotEquals(message1, message2);
    }

    @Test
    public void testHashCode() {
        RegistrationExceptionMessage message = new RegistrationExceptionMessage("Test Exception");
        assertEquals(message.hashCode(), message.hashCode());
    }
}
