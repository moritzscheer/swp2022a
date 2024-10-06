package de.uol.swp.common.message;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import de.uol.swp.common.user.Session;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ExceptionMessageTest {

    private AbstractServerMessage serverMessage;
    private String message = "Exception Message";

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    void setUp() {
        serverMessage = new AbstractServerMessage() {};
    }

    /**
     * Tests the constructor and the getters
     *
     * @author WKempel
     * @result The getters should return the correct ExceptionMessage
     * @see de.uol.swp.common.message.ExceptionMessage
     * @since 2023-06-27
     */
    @Test
    public void testConstructorAndGetters() {
        ExceptionMessage exceptionMessage = new ExceptionMessage(message);
        assertEquals(message, exceptionMessage.getException());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the ExceptionMessages are equal
     * @see de.uol.swp.common.message.ExceptionMessage
     * @since 2023-06-27
     */
    @Test
    public void testExceptionEquals() {
        ExceptionMessage exceptionMessage1 = new ExceptionMessage(message);
        ExceptionMessage exceptionMessage2 = new ExceptionMessage(message);
        assertEquals(exceptionMessage1, exceptionMessage2);
    }

    /**
     * Tests the hashCode method
     *
     * @author WKempel
     * @result The hashCode method should return the same hashCode for equal ExceptionMessages
     * @see de.uol.swp.common.message.ExceptionMessage
     * @since 2023-06-27
     */
    @Test
    public void testHashCode() {
        ExceptionMessage exceptionMessage1 = new ExceptionMessage(message);
        ExceptionMessage exceptionMessage2 = new ExceptionMessage(message);
        assertEquals(exceptionMessage1.hashCode(), exceptionMessage2.hashCode());
    }

    /**
     * Tests the setReceiver and getReceiver methods
     *
     * @author WKempel
     * @result The setter should set the correct receiver
     * @see de.uol.swp.common.message.AbstractServerMessage
     * @since 2023-06-27
     */
    @Test
    void testSetReceiver() {
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
    void testGetReceiver() {
        List<Session> receiver = new ArrayList<>();
        serverMessage.setReceiver(receiver);
        assertEquals(receiver, serverMessage.getReceiver());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the AbstractServerMessages are equal and
     *     false if they are not
     * @see de.uol.swp.common.message.AbstractServerMessage
     * @since 2023-06-17
     */
    @Test
    void testEquals() {
        AbstractServerMessage message1 = new AbstractServerMessage() {};

        AbstractServerMessage message2 = new AbstractServerMessage() {};

        // Same object
        assertEquals(message1, message1);
        // Different objects
        assertNotEquals(message1, message2);
    }
}
