package de.uol.swp.client.lobby.game.events;

import de.uol.swp.common.user.UserDTO;

public class ShowGameOverEvent {
    private final int lobbyID;
    private final UserDTO userWon;

    public ShowGameOverEvent(int lobbyID, UserDTO userWon) {
        this.lobbyID = lobbyID;
        this.userWon = userWon;
    }

    public UserDTO getUserWon() {
        return userWon;
    }

    public int getLobbyID() {
        return lobbyID;
    }
}
