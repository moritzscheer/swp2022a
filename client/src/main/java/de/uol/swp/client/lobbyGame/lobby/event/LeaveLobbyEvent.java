package de.uol.swp.client.lobbyGame.lobby.event;

import de.uol.swp.common.user.UserDTO;

/**
 * This event class is used by the presenters to tell the LobbyService to create a new request to
 * the server
 *
 * @author Maria Andrade
 * @since 2023-05-16
 */
public class LeaveLobbyEvent {
    private final Integer lobbyID;
    private final String lobbyName;
    private final UserDTO loggedInUser;
    private final Boolean multiplayer;

    public LeaveLobbyEvent(
            UserDTO loggedInUser, Integer lobbyID, String lobbyName, Boolean multiplayer) {
        this.lobbyID = lobbyID;
        this.loggedInUser = loggedInUser;
        this.lobbyName = lobbyName;
        this.multiplayer = multiplayer;
    }

    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }

    public Integer getLobbyID() {
        return lobbyID;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public Boolean isMultiplayer() {
        return multiplayer;
    }
}
