package de.uol.swp.common.user.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationExceptionMessageTest {

    private RegistrationExceptionMessage exceptionMessage;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    void setUp() {
        exceptionMessage = new RegistrationExceptionMessage("Test Exception");
    }

    /**
     * Tests the toString method
     *
     * @author WKempel
     * @result The toString method should return "RegistrationExceptionMessage Test Exception " +
     *     the String representation
     * @see de.uol.swp.common.user.exception.RegistrationExceptionMessage
     * @since 2023-06-17
     */
    @Test
    void testToString() {
        assertEquals("RegistrationExceptionMessage Test Exception", exceptionMessage.toString());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the RegistrationExceptionMessages are equal
     * @see de.uol.swp.common.user.exception.RegistrationExceptionMessage
     * @since 2023-06-17
     */
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

    /**
     * Tests the hashCode method
     *
     * @author WKempel
     * @result The hashCode method should return the same hashCode for the same
     *     RegistrationExceptionMessage
     * @see de.uol.swp.common.user.exception.RegistrationExceptionMessage
     * @since 2023-06-30
     */
    @Test
    public void testHashCode() {
        RegistrationExceptionMessage message = new RegistrationExceptionMessage("Test Exception");
        assertEquals(message.hashCode(), message.hashCode());
    }
}
