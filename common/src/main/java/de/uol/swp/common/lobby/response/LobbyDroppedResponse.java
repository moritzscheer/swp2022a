package de.uol.swp.common.lobby.response;

import de.uol.swp.common.user.UserDTO;

public class LobbyDroppedResponse extends AbstractLobbyResponse{
    private Integer lobbyID;

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2019-10-08
     */
    public LobbyDroppedResponse() {
    }

    /**
     * Constructor
     *
     * @param lobbyName name of the lobby
     * @param user user who wants to leave the lobby
     * @since 2019-10-08
     */
    public LobbyDroppedResponse(String lobbyName, UserDTO user, Integer lobbyID) {
        super(lobbyName, user);
        this.lobbyID = lobbyID;
    }

    public Integer getLobbyID() {
        return lobbyID;
    }
}
