package de.uol.swp.common.lobby.message;

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
    public LobbyDroppedMessage(String lobbyName, UserDTO user) {
        super(lobbyName, user);
    }

}
