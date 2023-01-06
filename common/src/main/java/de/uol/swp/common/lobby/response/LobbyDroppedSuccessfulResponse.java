package de.uol.swp.common.lobby.response;

import de.uol.swp.common.user.UserDTO;

/**
 * Response sent to the Client when a lobby is dropped
 *
 * @see de.uol.swp.common.user.User
 * @see de.uol.swp.common.lobby.response.AbstractLobbyResponse
 * @author Daniel Merzo
 * @since 2022-12-15
 */
public class LobbyDroppedSuccessfulResponse extends AbstractLobbyResponse{
    private Integer lobbyID;

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public LobbyDroppedSuccessfulResponse() {
    }

    /**
     * Constructor
     *
     * @param lobbyName name of the lobby
     * @param user user who wants to leave the lobby
     * @param lobbyID To identify the lobby with a unique key
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public LobbyDroppedSuccessfulResponse(String lobbyName, UserDTO user, Integer lobbyID) {
        super(lobbyName, user);
        this.lobbyID = lobbyID;
    }

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the Lobby ID
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public Integer getLobbyID() {
        return lobbyID;
    }
}
