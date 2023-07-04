package de.uol.swp.client.lobbyGame.game.events;

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

    /**
     * constructor
     *
     * @author Maria Andrade
     * @since 2023-05-18
     */
    public RequestDistributeCardsEvent(LobbyDTO lobby, UserDTO loggedInUser) {
        this.lobby = lobby;
        this.loggedInUser = loggedInUser;
    }

    /**
     * Getter method for the lobby
     *
     * @author Maria Andrade
     * @return the lobby
     * @since 2023-05-18
     */
    public LobbyDTO getLobby() {
        return lobby;
    }

    /**
     * Getter method for current logged user
     *
     * @author Maria Andrade
     * @return the current logged-in user
     * @since 2023-05-18
     */
    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }
}
