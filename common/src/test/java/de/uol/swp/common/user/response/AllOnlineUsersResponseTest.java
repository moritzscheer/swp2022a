package de.uol.swp.common.user.response;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AllOnlineUsersResponseTest {

    private AllOnlineUsersResponse response;
    private List<UserDTO> users;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-17
     */
    @BeforeEach
    public void setUp() {
        UserDTO user1 = new UserDTO("user1", "password1", "email1");
        UserDTO user2 = new UserDTO("user2", "password2", "email2");
        UserDTO user3 = new UserDTO("user3", "password3", "email3");

        users =
                Arrays.asList(
                        UserDTO.createWithoutPassword(user1),
                        UserDTO.createWithoutPassword(user2),
                        UserDTO.createWithoutPassword(user3));

        response = new AllOnlineUsersResponse(new ArrayList<>(users));
    }

    /**
     * Tests the getUsers method
     *
     * @author WKempel
     * @result The getter should return the correct users
     * @see de.uol.swp.common.user.response.AllOnlineUsersResponse
     * @since 2023-06-17
     */
    @Test
    public void testGetUsers() {
        List<UserDTO> actualUsers = response.getUsers();
        assertEquals(users, actualUsers);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The method should return true if the objects are equal
     * @see de.uol.swp.common.user.response.AllOnlineUsersResponse
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
     * @result The method should return true if the objects are equal
     * @see de.uol.swp.common.user.response.AllOnlineUsersResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsSameValuesThanTrue() {
        List<UserDTO> otherUsers = new ArrayList<>(users);
        AllOnlineUsersResponse other = new AllOnlineUsersResponse(new ArrayList<>(otherUsers));
        assertEquals(response, other);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The method should return false if the objects are different
     * @see de.uol.swp.common.user.response.AllOnlineUsersResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsDifferentTypeThanFalse() {
        assertNotEquals("not an AllOnlineUsersResponse object", response);
    }

    /**
     * Tests the equals method
     *
     * @author WKempel
     * @result The method should return false if the objects are null
     * @see de.uol.swp.common.user.response.AllOnlineUsersResponse
     * @since 2023-06-17
     */
    @Test
    public void testEqualsNullThanFalse() {
        assertNotEquals(null, response);
    }
}
