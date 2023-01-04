package de.uol.swp.common.lobby.response;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

/**
 * Response sent to the Client when a user leave the lobby
 *
 * @see de.uol.swp.common.user.User
 * @see de.uol.swp.common.lobby.response.AbstractLobbyResponse
 * @author Daniel Merzo
 * @since 2022-12-15
 */
public class LobbyLeaveUserResponse extends AbstractLobbyResponse{
    private LobbyDTO lobby;

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public LobbyLeaveUserResponse() {
    }

    /**
     * Constructor
     *
     * @param lobby containing the lobby data
     * @param user user who wants to leave the lobby
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public LobbyLeaveUserResponse(LobbyDTO lobby, UserDTO user) {
        super(lobby.getName(), user);
        this.lobby = lobby.createWithoutPassword(lobby);
    }

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the Lobby ID
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public LobbyDTO getLobby() {
        return lobby;
    }
}
