package de.uol.swp.common.chat.message;

import de.uol.swp.common.lobby.message.AbstractLobbyMessage;

public class TextHistoryMessage extends AbstractLobbyMessage {

    private final int lobbyID;

    private final String message;

    public TextHistoryMessage(int lobbyID, String message) {
        this.message = message;
        this.lobbyID = lobbyID;
    }

    public String getMessage() {
        return message;
    }

    public int getLobbyID() {
        return lobbyID;
    }
}
