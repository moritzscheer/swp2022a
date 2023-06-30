package de.uol.swp.common.user.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateUserExceptionMessageTest {

    private UpdateUserExceptionMessage exceptionMessage;

    @BeforeEach
    void setUp() {
        exceptionMessage = new UpdateUserExceptionMessage("Test Exception");
    }

    @Test
    void testToString() {
        assertEquals("UpdateUserExceptionMessage Test Exception", exceptionMessage.toString());
    }

    @Test
    void testEquals() {
        UpdateUserExceptionMessage message1 = new UpdateUserExceptionMessage("Test Exception");
        UpdateUserExceptionMessage message2 = new UpdateUserExceptionMessage("Different Exception");

        // Same object
        assertEquals(message1, message1);
        // Different objects
        assertNotEquals(message1, message2);
    }

    @Test
    public void testHashCode() {
        UpdateUserExceptionMessage message = new UpdateUserExceptionMessage("Test Exception");
        assertEquals(message.hashCode(), message.hashCode());
    }
}
