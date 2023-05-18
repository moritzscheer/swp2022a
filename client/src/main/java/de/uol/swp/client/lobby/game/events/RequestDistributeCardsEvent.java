package de.uol.swp.client.lobby.game.events;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

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
