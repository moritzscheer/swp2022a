package de.uol.swp.common.user.request;

import de.uol.swp.common.message.AbstractRequestMessage;

/**
 * Request for initialising the user list in the lobby
 *
 * This message is sent during the initialization of the user list. The server will
 * respond with a AllUsersInLobbyResponse.
 *
 * @see de.uol.swp.common.user.response.AllUsersInLobbyResponse
 * @since 2022-11-20
 */
public class RetrieveAllUsersInLobbyRequest extends AbstractRequestMessage {

    private String lobbyName;

    public RetrieveAllUsersInLobbyRequest(String lobbyName) {
        this.lobbyName = lobbyName;
    }

    public String getLobbyName() {
        return lobbyName;
    }
}
