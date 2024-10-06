package de.uol.swp.common.user.request;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateUserRequestTest {

    private UpdateUserRequest request;
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
        request = new UpdateUserRequest(user);
    }

    /**
     * Tests the getUser method
     *
     * @author WKempel
     * @result The getUser method should return the correct user
     * @see de.uol.swp.common.user.request.UpdateUserRequest
     * @since 2023-06-17
     */
    @Test
    public void testGetUser() {
        assertEquals(user, request.getUser());
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the UpdateUserRequests are equal
     * @see de.uol.swp.common.user.request.UpdateUserRequest
     * @since 2023-06-17
     */
    @Test
    public void testEqualSameObjectThanTrue() {
        assertEquals(request, request);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the UpdateUserRequests have the same values
     * @see de.uol.swp.common.user.request.UpdateUserRequest
     * @since 2023-06-17
     */
    @Test
    public void testEqualSameValuesThanTrue() {
        UpdateUserRequest other = new UpdateUserRequest(user);
        assertEquals(request, other);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the UpdateUserRequests have different values
     * @see de.uol.swp.common.user.request.UpdateUserRequest
     * @since 2023-06-17
     */
    @Test
    public void testEqualsDifferentTypeThanFalse() {
        assertNotEquals("not an UpdateUserRequest object", request);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the UpdateUserRequests is null
     * @see de.uol.swp.common.user.request.UpdateUserRequest
     * @since 2023-06-17
     */
    @Test
    public void testEqualsNulThanFalse() {
        assertNotEquals(null, request);
    }

    /**
     * Tests the hasCode method
     *
     * @author WKempel
     * @result The hashCode method should return the correct hash code
     * @see de.uol.swp.common.user.request.UpdateUserRequest
     * @since 2023-06-30
     */
    @Test
    public void testHashCode() {
        UpdateUserRequest request = new UpdateUserRequest(user);
        assertEquals(request.hashCode(), request.hashCode());
    }
}
