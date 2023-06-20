package de.uol.swp.client.lobbyGame.game.events;

import de.uol.swp.common.lobby.dto.LobbyDTO;

/**
 * This event class is used by the presenters to tell the GameService to create a new request to the
 * server
 *
 * @author Maria Andrade
 * @since 2023-05-16
 */
public class RequestStartGameEvent {

    private final int numberBots;
    private final int numberCheckpoints;

    private final LobbyDTO lobbyDTO;

    public RequestStartGameEvent(int numberBots, int numberCheckpoints, LobbyDTO lobbyDTO) {
        this.numberBots = numberBots;
        this.numberCheckpoints = numberCheckpoints;
        this.lobbyDTO = lobbyDTO;
    }

    public LobbyDTO getLobbyDTO() {
        return lobbyDTO;
    }

    public int getNumberBots() {
        return numberBots;
    }

    public int getNumberCheckpoints() {
        return numberCheckpoints;
    }
}
