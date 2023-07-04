package de.uol.swp.client.lobbyGame.lobby.event;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

/**
 * This event class is used by the presenters to tell the LobbyService to create a new request to
 * the server
 *
 * @author Maria Andrade
 * @since 2023-05-16
 */
public class UserJoinLobbyEvent {
    private final LobbyDTO lobby;
    private final UserDTO loggedInUser;
    private final String password;

    /**
     * Constructor for the class
     *
     * @author Maria Andrade
     * @since 2023-05-16
     */
    public UserJoinLobbyEvent(LobbyDTO lobby, UserDTO loggedInUser, String password) {

        this.lobby = lobby;
        this.loggedInUser = loggedInUser;
        this.password = password;
    }
    /**
     * Getter for the lobby variable
     *
     * @author Maria Andrade
     * @since 2023-05-16
     */
    public LobbyDTO getLobby() {
        return lobby;
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
     * Getter for the password variable
     *
     * @author Maria Andrade
     * @since 2023-05-16
     */
    public String getPassword() {
        return password;
    }
}
