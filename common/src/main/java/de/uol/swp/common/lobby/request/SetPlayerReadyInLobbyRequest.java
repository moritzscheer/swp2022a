package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;

/**
 * This event class is used by the presenters to tell the LobbyService to set the player who send
 * this event to ready. This is important for the start of the lobby
 *
 * @author Moritz Scheer
 * @since 2023-05-28
 */
public class SetPlayerReadyInLobbyRequest extends AbstractLobbyRequest {

    private final Integer lobbyID;
    private final Boolean ready;

    /**
     * Constructor
     *
     * @param lobbyID Integer containing the identifier of the lobby
     * @param user User that is now ready
     * @param ready status if a player is ready
     * @author Moritz Scheer
     * @since 2023-05-28
     */
    public SetPlayerReadyInLobbyRequest(Integer lobbyID, UserDTO user, Boolean ready) {
        super("", user);
        this.lobbyID = lobbyID;
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
