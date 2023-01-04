package de.uol.swp.common.lobby.message;

import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

/**
 * Message sent by the server when a lobby is dropped
 *
 * @see de.uol.swp.common.lobby.message.AbstractLobbyMessage
 * @see de.uol.swp.common.user.User
 * @author Daniel Merzo
 * @since 2022-12-15
 */
public class LobbyDroppedMessage extends AbstractLobbyMessage{

    private LobbyDTO lobby;

    /**
     * Default constructor
     *
     * @implNote this constructor is needed for serialization
     * @since 2019-10-08
     */
    public LobbyDroppedMessage() {
    }

    /**
     * Constructor
     *
     * @param lobbyName name of the lobby
     * @param user user who joined the lobby
     * @since 2019-10-08
     */
    public LobbyDroppedMessage(LobbyDTO lobby, String lobbyName, UserDTO user) {
        super(lobbyName, user);
        this.lobby = lobby.createWithoutPassword(lobby);
    }

    /**
     * Getter for the lobby variable
     *
     * @return LobbyDTO containing the lobby's data
     * @author Moritz Scheer
     * @since 2023-01-03
     */
    public LobbyDTO getLobby() {
        return lobby;
    }
}
