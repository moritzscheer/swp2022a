package de.uol.swp.common.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import de.uol.swp.common.user.Session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class AbstractServerMessageTest {

    private AbstractServerMessage serverMessage;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    public void setUp() {
        serverMessage = new AbstractServerMessage() {};
    }

    /**
     * Tests the setSender and getSender methods
     *
     * @author WKempel
     * @result The getter should return the correct sender
     * @see de.uol.swp.common.message.AbstractServerMessage
     * @since 2023-06-17
     */
    @Test
    public void testSetReceiver() {
        List<Session> receiver = new ArrayList<>();
        serverMessage.setReceiver(receiver);
        assertEquals(receiver, serverMessage.getReceiver());
    }

    /**
     * Tests the getReceiver method
     *
     * @author WKempel
     * @result The getter should return the correct receiver
     * @see de.uol.swp.common.message.AbstractServerMessage
     * @since 2023-06-17
     */
    @Test
    public void testGetReceiver() {
        List<Session> receiver = new ArrayList<>();
        serverMessage.setReceiver(receiver);
        assertEquals(receiver, serverMessage.getReceiver());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The method should return true if the objects are the same and false if they are different
     * @see de.uol.swp.common.message.AbstractServerMessage
     * @since 2023-06-17
     */
    @Test
   public void testEquals() {
        AbstractServerMessage message1 = new AbstractServerMessage() {};

        AbstractServerMessage message2 = new AbstractServerMessage() {};

        // Same object
        assertEquals(message1, message1);
        // Different objects
        assertNotEquals(message1, message2);
    }
}
