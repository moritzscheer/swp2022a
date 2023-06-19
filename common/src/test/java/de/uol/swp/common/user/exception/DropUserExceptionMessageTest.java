package de.uol.swp.common.user.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DropUserExceptionMessageTest {

    private DropUserExceptionMessage exceptionMessage;

    @BeforeEach
    void setUp() {
        exceptionMessage = new DropUserExceptionMessage("Test Exception");
    }

    @Test
    void testToString() {
        assertEquals("DropUserExceptionMessage Test Exception", exceptionMessage.toString());
    }

    @Test
    void testEquals() {
        DropUserExceptionMessage message1 = new DropUserExceptionMessage("Test Exception");
        DropUserExceptionMessage message2 = new DropUserExceptionMessage("Different Exception");

        // Same object
        assertEquals(message1, message1);
        // Different objects
        assertNotEquals(message1, message2);
    }
}
