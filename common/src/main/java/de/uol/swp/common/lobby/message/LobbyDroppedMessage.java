package de.uol.swp.common.lobby.message;

import de.uol.swp.common.user.UserDTO;

public class LobbyDroppedMessage extends AbstractLobbyMessage{

    public LobbyDroppedMessage(String name, UserDTO user) {
        super(name, user);
    }
}
