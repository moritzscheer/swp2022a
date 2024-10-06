package de.uol.swp.common.user.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DropUserExceptionMessageTest {

    private DropUserExceptionMessage exceptionMessage;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    void setUp() {
        exceptionMessage = new DropUserExceptionMessage("Test Exception");
    }

    /**
     * Tests the toString method
     *
     * @author WKempel
     * @result The toString method should return "DropUserExceptionMessage Test Exception " + the
     *     String representation
     * @see de.uol.swp.common.user.exception.DropUserExceptionMessage
     * @since 2023-06-17
     */
    @Test
    void testToString() {
        assertEquals("DropUserExceptionMessage Test Exception", exceptionMessage.toString());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the DropUserExceptionMessages are equal
     * @see de.uol.swp.common.user.exception.DropUserExceptionMessage
     * @since 2023-06-17
     */
    @Test
    void testEquals() {
        DropUserExceptionMessage message1 = new DropUserExceptionMessage("Test Exception");
        DropUserExceptionMessage message2 = new DropUserExceptionMessage("Different Exception");

        // Same object
        assertEquals(message1, message1);
        // Different objects
        assertNotEquals(message1, message2);
    }

    /**
     * Tests the hashCode method
     *
     * @author WKempel
     * @result The hashCode method should return the same hashCode for the same
     *     DropUserExceptionMessage
     * @see de.uol.swp.common.user.exception.DropUserExceptionMessage
     * @since 2023-06-17
     */
    @Test
    public void testHashCode() {
        DropUserExceptionMessage message = new DropUserExceptionMessage("Test Exception");
        assertEquals(message.hashCode(), message.hashCode());
    }
}
