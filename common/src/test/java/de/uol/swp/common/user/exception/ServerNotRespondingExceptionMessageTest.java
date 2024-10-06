package de.uol.swp.common.user.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class ServerNotRespondingExceptionMessageTest {

    /**
     * Tests the constructor
     *
     * @author WKempel
     * @result The constructor should create a new ServerNotRespondingExceptionMessage with the
     *     content "An error occurred"
     * @see de.uol.swp.common.user.exception.ServerNotRespondingExceptionMessage
     * @since 2023-06-30
     */
    @Test
    public void testConstructor() {
        ServerNotRespondingExceptionMessage actualServerNotRespondingExceptionMessage =
                new ServerNotRespondingExceptionMessage("An error occurred");
        assertEquals(
                "ServerNotRespondingMessage An error occurred",
                actualServerNotRespondingExceptionMessage.toString());
    }

    /**
     * Tests the toString method
     *
     * @author WKempel
     * @result The toString method should return "ServerNotRespondingMessage An error occurred"
     * @see de.uol.swp.common.user.exception.ServerNotRespondingExceptionMessage
     * @since 2023-06-30
     */
    @Test
    public void testToString() {
        assertEquals(
                "ServerNotRespondingMessage An error occurred",
                (new ServerNotRespondingExceptionMessage("An error occurred")).toString());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the ServerNotRespondingExceptionMessages are
     *     equal and false if they are not
     * @see de.uol.swp.common.user.exception.ServerNotRespondingExceptionMessage
     * @since 2023-06-30
     */
    @Test
    public void testEquals() {
        ServerNotRespondingExceptionMessage message1 =
                new ServerNotRespondingExceptionMessage("Test Exception");
        ServerNotRespondingExceptionMessage message2 =
                new ServerNotRespondingExceptionMessage("Different Exception");

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
     *     ServerNotRespondingExceptionMessage
     * @see de.uol.swp.common.user.exception.ServerNotRespondingExceptionMessage
     * @since 2023-06-30
     */
    @Test
    public void testHashCode() {
        ServerNotRespondingExceptionMessage message =
                new ServerNotRespondingExceptionMessage("Test Exception");
        assertEquals(message.hashCode(), message.hashCode());
    }
}
