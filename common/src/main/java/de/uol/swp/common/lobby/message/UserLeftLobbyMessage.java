package de.uol.swp.common.lobby.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

/**
 * Message sent by the server when a user successfully leaves a lobby
 *
 * @see de.uol.swp.common.lobby.message.AbstractLobbyMessage
 * @see de.uol.swp.common.user.User
 * @author Marco Grawunder
 * @since 2019-10-08
 */
public class UserLeftLobbyMessage extends AbstractLobbyMessage {

    private LobbyDTO lobby;
    private Integer lobbyID;

    /**
     * Constructor
     *
     * @param lobbyName name of the lobby
     * @param user user who left the lobby
     * @param lobby LobbyDTO Object containing all the information of the lobby
     * @since 2019-10-08
     */
    public UserLeftLobbyMessage(Integer lobbyID, String lobbyName, UserDTO user, LobbyDTO lobby) {
        super(lobbyName, user);
        this.lobby = lobby;
        this.lobbyID = lobbyID;
    }

    /**
     * Getter for the lobbyDTO variable
     *
     * @return LobbyDTO Object containing all the information of the lobby
     * @author Moritz Scheer
     * @since 2023-04-07
     */
    public LobbyDTO getLobby() {
        return lobby;
    }

    /**
     * getter for the new Lobby data
     *
     * @return LobbyDTO containing the lobby information
     * @author Moritz Scheer
     * @since 2023-01-03
     */
    public Integer getLobbyID() {
        return lobbyID;
    }
}
