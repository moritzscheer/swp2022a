package de.uol.swp.common.user.request;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnToMainMenuRequestTest {

    private ReturnToMainMenuRequest request;
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
        request = new ReturnToMainMenuRequest(user);
    }

    /**
     * Tests the getLoggedInUser method
     *
     * @author WKempel
     * @result The getLoggedInUser method should return the correct user
     * @see de.uol.swp.common.user.request.ReturnToMainMenuRequest
     * @since 2023-06-17
     */
    @Test
    public void getLoggedInUser() {
        assertEquals(user, request.getLoggedInUser());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the ReturnToMainMenuRequests are equal
     * @see de.uol.swp.common.user.request.ReturnToMainMenuRequest
     * @since 2023-06-17
     */
    @Test
    public void testEqualsSameObjectThanTrue() {
        assertEquals(request, request);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the ReturnToMainMenuRequests have the same values
     * @see de.uol.swp.common.user.request.ReturnToMainMenuRequest
     * @since 2023-06-17
     */
    @Test
    public void testEqualsSameValuesThanTrue() {
        ReturnToMainMenuRequest other = new ReturnToMainMenuRequest(user);
        assertEquals(request, other);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the ReturnToMainMenuRequests have different values
     * @see de.uol.swp.common.user.request.ReturnToMainMenuRequest
     * @since 2023-06-17
     */
    @Test
    public void testEqualsDifferentTypeThanFalse() {
        assertNotEquals("not a ReturnToMainMenuRequest object", request);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the ReturnToMainMenuRequests is null
     * @see de.uol.swp.common.user.request.ReturnToMainMenuRequest
     * @since 2023-06-17
     */
    @Test
    public void testEqualsNullThanFalse() {
        assertNotEquals(null, request);
    }
}
