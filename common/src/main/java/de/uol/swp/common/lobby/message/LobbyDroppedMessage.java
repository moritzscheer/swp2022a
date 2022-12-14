package de.uol.swp.common.lobby.message;

import de.uol.swp.common.user.UserDTO;

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
     * @param user user who left the lobby
     * @since 2019-10-08
     */
    public LobbyDroppedMessage(String lobbyName, UserDTO user) {
        super(lobbyName, user);
    }
}
