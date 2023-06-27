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

    @BeforeEach
    void setUp() {
        serverMessage = new AbstractServerMessage() {};
    }

    @Test
    public void testConstructorAndGetters() {
        ExceptionMessage exceptionMessage = new ExceptionMessage(message);
        assertEquals(message, exceptionMessage.getException());
    }

    @Test
    public void testExceptionEquals() {
        ExceptionMessage exceptionMessage1 = new ExceptionMessage(message);
        ExceptionMessage exceptionMessage2 = new ExceptionMessage(message);
        assertEquals(exceptionMessage1, exceptionMessage2);
    }

    @Test
    public void testHashCode() {
        ExceptionMessage exceptionMessage1 = new ExceptionMessage(message);
        ExceptionMessage exceptionMessage2 = new ExceptionMessage(message);
        assertEquals(exceptionMessage1.hashCode(), exceptionMessage2.hashCode());
    }
    @Test
    void testSetReceiver() {
        List<Session> receiver = new ArrayList<>();
        serverMessage.setReceiver(receiver);
        assertEquals(receiver, serverMessage.getReceiver());
    }

    @Test
    void testGetReceiver() {
        List<Session> receiver = new ArrayList<>();
        serverMessage.setReceiver(receiver);
        assertEquals(receiver, serverMessage.getReceiver());
    }

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
