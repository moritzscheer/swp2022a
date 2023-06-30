package de.uol.swp.common.user.response;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDroppedSuccessfulResponseTest {

    private UserDroppedSuccessfulResponse response;
    private String userDropped;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    public void setUp() {
        userDropped = "username";
        response = new UserDroppedSuccessfulResponse(userDropped);
    }

    /**
     * Tests the getUsername method
     *
     * @author WKempel
     * @result The getUsername method should return the correct username
     * @see de.uol.swp.common.user.response.UserDroppedSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testGetUsername() {
        String actualUsername = response.getUsername();
        assertEquals(userDropped, actualUsername);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the UserDroppedSuccessfulResponses are equal
     * @see de.uol.swp.common.user.response.UserDroppedSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualSameObjectThanTrue() {
        assertEquals(response, response);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the UserDroppedSuccessfulResponses are equal
     * @see de.uol.swp.common.user.response.UserDroppedSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsSameValuesThanTrue() {
        UserDroppedSuccessfulResponse other = new UserDroppedSuccessfulResponse(userDropped);
        assertEquals(response, other);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the UserDroppedSuccessfulResponses are not equal
     * @see de.uol.swp.common.user.response.UserDroppedSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsDifferentTypeThanFalse() {
        assertNotEquals("not a UserDroppedSuccessfulResponse object", response);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the UserDroppedSuccessfulResponses are not equal
     * @see de.uol.swp.common.user.response.UserDroppedSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsNullThanFalse()  {
        assertNotEquals(null, response);
    }
}
