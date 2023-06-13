package de.uol.swp.common.game.request;

import de.uol.swp.common.lobby.request.AbstractLobbyRequest;
import de.uol.swp.common.user.UserDTO;

public class TurnRobotOffRequest extends AbstractLobbyRequest {
    private final int lobbyID;
    private final UserDTO loggedInUser;
    public TurnRobotOffRequest(Integer lobbyID, UserDTO loggedInUser) {
        this.lobbyID = lobbyID;
        this.loggedInUser = loggedInUser;
    }

    public int getLobbyID() {
        return lobbyID;
    }

    public UserDTO getLoggedInUser() {
        return loggedInUser;
    }
}
