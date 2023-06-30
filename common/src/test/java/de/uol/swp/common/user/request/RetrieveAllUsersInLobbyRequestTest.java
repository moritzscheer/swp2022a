package de.uol.swp.common.user.request;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RetrieveAllUsersInLobbyRequestTest {

    @Test
    public void testRetrieveAllUsersInLobbyRequest() {
        RetrieveAllUsersInLobbyRequest request = new RetrieveAllUsersInLobbyRequest("lobbyName");
        assertEquals("lobbyName", request.getLobbyName());
    }

    @Test
    public void testGetLobbyName() {
        RetrieveAllUsersInLobbyRequest request = new RetrieveAllUsersInLobbyRequest("lobbyName");
        assertEquals("lobbyName", request.getLobbyName());
    }
}
