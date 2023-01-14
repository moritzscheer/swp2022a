package de.uol.swp.common.lobby.request;

import de.uol.swp.common.user.UserDTO;

/**
 * Request sent to the server when a user wants to leave a lobby
 *
 * @see de.uol.swp.common.lobby.request.AbstractLobbyRequest
 * @see de.uol.swp.common.user.User
 * @author Marco Grawunder
 * @since 2019-10-08
 */
public class LeaveLobbyRequest extends AbstractLobbyRequest {

    private Integer lobbyID;
    private Boolean multiplayer;

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2019-10-08
     */
    public LeaveLobbyRequest() {}

    /**
     * Constructor
     *
     * @param lobbyName name of the lobby
     * @param user user who wants to leave the lobby
     * @param lobbyID To identify the lobby with a unique key
     * @param multiplayer Boolean value to query if the user is in the multiplayer
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public LeaveLobbyRequest(String lobbyName, UserDTO user, Integer lobbyID, Boolean multiplayer) {
        super(lobbyName, user);
        this.lobbyID = lobbyID;
        this.multiplayer = multiplayer;
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

    /**
     * Getter for the multiplayer variable
     *
     * @return Boolean containing the multiplayer
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public Boolean isMultiplayer() {
        return multiplayer;
    }
}
