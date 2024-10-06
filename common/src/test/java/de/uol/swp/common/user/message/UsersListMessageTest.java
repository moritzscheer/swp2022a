package de.uol.swp.common.user.message;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class UsersListMessageTest {

    private UsersListMessage usersListMessage;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    void setUp() {
        List<String> users = Arrays.asList("user1", "user2", "user3");
        usersListMessage = new UsersListMessage(users);
    }

    /**
     * Tests the getUsers method
     *
     * @author WKempel
     * @result The getter should return the correct users
     * @see de.uol.swp.common.user.message.UsersListMessage
     * @since 2023-06-17
     */
    @Test
    void testGetUsers() {
        List<String> users = usersListMessage.getUsers();
        assertEquals(3, users.size());
        assertTrue(users.contains("user1"));
        assertTrue(users.contains("user2"));
        assertTrue(users.contains("user3"));
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The method should return true if the objects are equal and false if they are
     *     different
     * @see de.uol.swp.common.user.message.UsersListMessage
     * @since 2023-06-17
     */
    @Test
    void testEquals() {
        List<String> users1 = Arrays.asList("user1", "user2", "user3");
        List<String> users2 = Arrays.asList("user1", "user2", "user3");
        List<String> users3 = Arrays.asList("user4", "user5", "user6");

        UsersListMessage message1 = new UsersListMessage(users1);
        UsersListMessage message2 = new UsersListMessage(users2);
        UsersListMessage message3 = new UsersListMessage(users3);

        // Same object
        assertEquals(message1, message1);
        // Equal objects
        assertEquals(message1, message2);
        // Different objects
        assertNotEquals(message1, message3);
    }
}
