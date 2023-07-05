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

    /**
     * Constructor
     *
     * @author Maria Andrade and Waldemar Kempel
     * @since 2023-05-16
     */
    public RequestStartGameEvent(int numberBots, int numberCheckpoints, LobbyDTO lobbyDTO) {
        this.numberBots = numberBots;
        this.numberCheckpoints = numberCheckpoints;
        this.lobbyDTO = lobbyDTO;
    }

    /**
     * Getter Method for Lobby DTO
     *
     * @author Maria Andrade
     * @return the lobbyDTO
     * @since 2023-05-16
     */
    public LobbyDTO getLobbyDTO() {
        return lobbyDTO;
    }

    /**
     * Getter Method for number of Bots
     *
     * @author Maria Andrade
     * @return the number of bots
     * @since 2023-06-05
     */
    public int getNumberBots() {
        return numberBots;
    }
    /**
     * Getter Method for number of checkpoints
     *
     * @author Maria Andrade
     * @return the number of checkpoints
     * @since 2023-05-16
     */
    public int getNumberCheckpoints() {
        return numberCheckpoints;
    }
}
