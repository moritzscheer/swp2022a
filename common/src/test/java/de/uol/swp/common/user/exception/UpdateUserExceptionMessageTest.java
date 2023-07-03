package de.uol.swp.common.user.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateUserExceptionMessageTest {

    private UpdateUserExceptionMessage exceptionMessage;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    void setUp() {
        exceptionMessage = new UpdateUserExceptionMessage("Test Exception");
    }

    /**
     * Tests the toString method
     *
     * @author WKempel
     * @result The toString method should return "UpdateUserExceptionMessage Test Exception " + the
     *     String representation
     * @see de.uol.swp.common.user.exception.UpdateUserExceptionMessage
     * @since 2023-06-17
     */
    @Test
    void testToString() {
        assertEquals("UpdateUserExceptionMessage Test Exception", exceptionMessage.toString());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the UpdateUserExceptionMessages are equal
     * @see de.uol.swp.common.user.exception.UpdateUserExceptionMessage
     * @since 2023-06-17
     */
    @Test
    void testEquals() {
        UpdateUserExceptionMessage message1 = new UpdateUserExceptionMessage("Test Exception");
        UpdateUserExceptionMessage message2 = new UpdateUserExceptionMessage("Different Exception");

        // Same object
        assertEquals(message1, message1);
        // Different objects
        assertNotEquals(message1, message2);
    }

    /**
     * Tests the hashCode method
     *
     * @author WKempel
     * @result The hashCode method should return the same hashCode for the same object
     * @see de.uol.swp.common.user.exception.UpdateUserExceptionMessage
     * @since 2023-06-17
     */
    @Test
    public void testHashCode() {
        UpdateUserExceptionMessage message = new UpdateUserExceptionMessage("Test Exception");
        assertEquals(message.hashCode(), message.hashCode());
    }
}
