package de.uol.swp.common.user.response;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdatedUserSuccessfulResponseTest {

    private UpdatedUserSuccessfulResponse response;
    private UserDTO updatedUser;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    public void setUp() {
        updatedUser = new UserDTO("username", "password", "email");
        response = new UpdatedUserSuccessfulResponse(updatedUser);
    }

    /**
     * Tests the getUpdatedUser method
     *
     * @author WKempel
     * @result The getUpdatedUser method should return the correct user
     * @see de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testGetUpdatedUser() {
        User actualUpdatedUser = response.getUpdatedUser();
        assertEquals(updatedUser, actualUpdatedUser);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return true if the UpdatedUserSuccessfulResponses are equal
     * @see de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse
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
     * @result The equals method should return true if the UpdatedUserSuccessfulResponses are equal
     * @see de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsSameValuesThanTrue() {
        UpdatedUserSuccessfulResponse other = new UpdatedUserSuccessfulResponse(updatedUser);
        assertEquals(response, other);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the UpdatedUserSuccessfulResponses are not
     *     equal
     * @see de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsDifferentTypeThanFalse() {
        assertNotEquals("not an UpdatedUserSuccessfulResponse object", response);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The equals method should return false if the UpdatedUserSuccessfulResponses are null
     * @see de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsNullThanFalse() {
        assertNotEquals(null, response);
    }
}
