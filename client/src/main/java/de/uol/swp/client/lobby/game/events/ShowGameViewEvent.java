package de.uol.swp.client.lobby.game.events;

import de.uol.swp.common.lobby.dto.LobbyDTO;

/**
 * Event used to show the Game window if the start button was pressed in the Lobby Window
 *
 * <p>In order to show the game window using this event, post an instance of it onto the eventBus
 * the SceneManager is subscribed to.
 *
 * @author Maxim Erden
 * @see de.uol.swp.client.SceneManager
 * @since 2023-02-20
 */
public class ShowGameViewEvent {

    private LobbyDTO lobby;
    private int gameID;
    private final int[][][][] boardImageIds;

    public ShowGameViewEvent(LobbyDTO lobby, int gameID, int[][][][] boardImageIds) {
        this.lobby = lobby;
        this.gameID = gameID;
        this.boardImageIds = boardImageIds;
    }

    public Integer getLobbyID() {
        return lobby.getLobbyID();
    }

    public LobbyDTO getLobby() {
        return lobby;
    }

    public int getGameID() {
        return gameID;
    }

    public int[][][][] getBoardImageIds() {
        return boardImageIds;
    }
}
