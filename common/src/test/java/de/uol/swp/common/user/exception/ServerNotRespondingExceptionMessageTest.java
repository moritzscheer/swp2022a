package de.uol.swp.common.user.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class ServerNotRespondingExceptionMessageTest {

    @Test
    public void testConstructor() {
        ServerNotRespondingExceptionMessage actualServerNotRespondingExceptionMessage = new ServerNotRespondingExceptionMessage("An error occurred");
        assertEquals("ServerNotRespondingMessage An error occurred", actualServerNotRespondingExceptionMessage.toString());
    }

    @Test
    public void testToString(){
        assertEquals("ServerNotRespondingMessage An error occurred", (new ServerNotRespondingExceptionMessage("An error occurred")).toString());
    }

    @Test
    public void testEquals() {
        ServerNotRespondingExceptionMessage message1 = new ServerNotRespondingExceptionMessage("Test Exception");
        ServerNotRespondingExceptionMessage message2 =
                new ServerNotRespondingExceptionMessage("Different Exception");

        // Same object
        assertEquals(message1, message1);
        // Different objects
        assertNotEquals(message1, message2);
    }

    @Test
    public void testHashCode() {
        ServerNotRespondingExceptionMessage message = new ServerNotRespondingExceptionMessage("Test Exception");
        assertEquals(message.hashCode(), message.hashCode());
    }

}
