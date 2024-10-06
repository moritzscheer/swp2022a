package de.uol.swp.common.user.message;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnToMainMenuMessageTest {

    private ReturnToMainMenuMessage message;
    private UserDTO user;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    public void setUp() {
        user = new UserDTO("username", "password", "email");
        message = new ReturnToMainMenuMessage(user);
    }

    /**
     * Tests the getLoggedInUser method
     *
     * @author WKempel
     * @result The getLoggedInUser method should return the correct user
     * @see de.uol.swp.common.user.message.ReturnToMainMenuMessage
     * @since 2023-06-17
     */
    @Test
    public void getLoggedInUser() {
        assertEquals(user, message.getLoggedInUser());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the ReturnToMainMenuRequests are equal
     * @see de.uol.swp.common.user.message.ReturnToMainMenuMessage
     * @since 2023-06-17
     */
    @Test
    public void testEqualsSameObjectThanTrue() {
        assertEquals(message, message);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the ReturnToMainMenuRequests have the same
     *     values
     * @see de.uol.swp.common.user.message.ReturnToMainMenuMessage
     * @since 2023-06-17
     */
    @Test
    public void testEqualsSameValuesThanTrue() {
        ReturnToMainMenuMessage other = new ReturnToMainMenuMessage(user);
        assertEquals(message, other);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the ReturnToMainMenuRequests have different
     *     values
     * @see de.uol.swp.common.user.message.ReturnToMainMenuMessage
     * @since 2023-06-17
     */
    @Test
    public void testEqualsDifferentTypeThanFalse() {
        assertNotEquals("not a ReturnToMainMenuMessage object", message);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the ReturnToMainMenuRequests is null
     * @see de.uol.swp.common.user.message.ReturnToMainMenuMessage
     * @since 2023-06-17
     */
    @Test
    public void testEqualsNullThanFalse() {
        assertNotEquals(null, message);
    }
}
