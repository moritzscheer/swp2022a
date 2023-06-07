package de.uol.swp.common.game.message;

import de.uol.swp.common.lobby.message.AbstractLobbyMessage;

/**
 * Message to distribute new cards and reset view for players
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-24
 */
public class RoundIsOverMessage extends AbstractLobbyMessage {
    private final int lobbyID;

    public RoundIsOverMessage(int lobbyID) {
        this.lobbyID = lobbyID;
    }

    public int getLobbyID() {
        return lobbyID;
    }

}
