package de.uol.swp.common.user.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class RetrieveAllUsersInLobbyRequestTest {

    /**
     * Tests the retrieveAllUsersInLobbyRequest method
     *
     * @author WKempel
     * @result The retrieveAllUsersInLobbyRequest method should return the correct lobbyName
     * @see de.uol.swp.common.user.request.RetrieveAllUsersInLobbyRequest
     * @since 2023-06-30
     */
    @Test
    public void testRetrieveAllUsersInLobbyRequest() {
        RetrieveAllUsersInLobbyRequest request = new RetrieveAllUsersInLobbyRequest("lobbyName");
        assertEquals("lobbyName", request.getLobbyName());
    }

    /**
     * Tests the getLobbyName method
     *
     * @author WKempel
     * @result The getLobbyName method should return the correct lobbyName
     * @see de.uol.swp.common.user.request.RetrieveAllUsersInLobbyRequest
     * @since 2023-06-30
     */
    @Test
    public void testGetLobbyName() {
        RetrieveAllUsersInLobbyRequest request = new RetrieveAllUsersInLobbyRequest("lobbyName");
        assertEquals("lobbyName", request.getLobbyName());
    }
}
