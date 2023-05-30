package de.uol.swp.common.lobby.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.message.AbstractServerMessage;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

/**
 * Response sent to the Client when a user leave the lobby
 *
 * @see de.uol.swp.common.user.User
 * @see de.uol.swp.common.lobby.response.AbstractLobbyResponse
 * @author Moritz Scheer
 * @since 2023-05-28
 */
public class PlayerReadyInLobbyMessage extends AbstractServerMessage {

    private final Integer lobbyID;
    private UserDTO user;
    private final Boolean ready;


    /**
     * Constructor
     *
     * @param lobbyID Integer containing the identifier of the lobby
     * @param user containing the user that is now ready
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    public PlayerReadyInLobbyMessage(Integer lobbyID, UserDTO user, Boolean ready) {
        this.lobbyID = lobbyID;
        this.user = user;
        this.ready = ready;
    }

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the identifier of the lobby
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    public int getLobbyID() {
        return lobbyID;
    }

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the Lobby ID
     * Moritz Scheer
     * @since 2023-05-28
     */
    public UserDTO getUser() {
        return user;
    }

    /**
     * Getter for the ready variable
     *
     * @return status if a player is ready
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    public Boolean isReady() {
        return ready;
    }
}