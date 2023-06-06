package de.uol.swp.client.lobby.game.events;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

/**
 * This event class is used by the LobbyGameManagement to tell the GameService to create a new
 * request to the server
 *
 * @author Maria Andrade
 * @since 2023-05-18
 */
public class RequestDistributeCardsEvent {

    private final LobbyDTO lobby;

    private final UserDTO loggedInUser;

    public RequestDistributeCardsEvent(LobbyDTO lobby, UserDTO loggedInUser) {
        this.lobby = lobby;
        this.loggedInUser = loggedInUser;
    }

    public LobbyDTO getLobby() {
        return lobby;
    }

    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }
}
