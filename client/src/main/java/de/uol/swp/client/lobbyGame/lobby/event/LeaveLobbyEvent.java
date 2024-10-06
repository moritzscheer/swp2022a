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

    /**
     * Constructor for the class
     *
     * @author Maria Andrade
     * @since 2022-05-16
     */
    public LeaveLobbyEvent(
            UserDTO loggedInUser, Integer lobbyID, String lobbyName, Boolean multiplayer) {
        this.lobbyID = lobbyID;
        this.loggedInUser = loggedInUser;
        this.lobbyName = lobbyName;
        this.multiplayer = multiplayer;
    }

    /**
     * Getter for the loggedInUser variable
     *
     * @author Maria Andrade
     * @since 2023-05-16
     */
    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }
    /**
     * Getter for the lobbyID variable
     *
     * @author Maria Andrade
     * @since 2023-05-16
     */
    public Integer getLobbyID() {
        return lobbyID;
    }
    /**
     * Getter for the lobbyName variable
     *
     * @author Maria Andrade
     * @since 2023-05-16
     */
    public String getLobbyName() {
        return lobbyName;
    }
    /**
     * Getter for the multiplayer variable
     *
     * @author Maria Andrade
     * @since 2023-05-16
     */
    public Boolean isMultiplayer() {
        return multiplayer;
    }
}
