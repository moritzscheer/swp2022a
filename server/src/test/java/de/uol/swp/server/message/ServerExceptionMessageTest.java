package de.uol.swp.server.message;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ServerExceptionMessageTest {

    /**
     * Tests the getException method
     *
     * @author WKempel
     * @since 2023-06-23
     */
    @Test
    public void testGetException() {
        Exception exception = new RuntimeException("Test exception");
        ServerExceptionMessage message = new ServerExceptionMessage(exception);

        Exception actualException = message.getException();

        Assertions.assertEquals(exception, actualException);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @since 2023-06-23
     */
    @Test
    public void testEquals() {
        Exception exception1 = new RuntimeException("Exception 1");
        Exception exception2 = new RuntimeException("Exception 2");

        ServerExceptionMessage message1 = new ServerExceptionMessage(exception1);
        ServerExceptionMessage message2 = new ServerExceptionMessage(exception1);
        ServerExceptionMessage message3 = new ServerExceptionMessage(exception2);

        // Same instance
        Assertions.assertEquals(message1, message1);

        // Same attributes but different instance
        Assertions.assertEquals(message1, message2);

        // Same attributes but different sequence
        Assertions.assertEquals(message2, message1);

        Assertions.assertEquals(message1.hashCode(), message2.hashCode());

        Assertions.assertNotEquals(message1, message3);
        Assertions.assertNotEquals(message3, message1);
    }
}
