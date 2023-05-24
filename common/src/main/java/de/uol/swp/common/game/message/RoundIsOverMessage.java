package de.uol.swp.common.game.message;

import de.uol.swp.common.lobby.message.AbstractLobbyMessage;

public class RoundIsOverMessage extends AbstractLobbyMessage {
    private final int lobbyID;

    public RoundIsOverMessage(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    public int getLobbyID() {
        return lobbyID;
    }

}
