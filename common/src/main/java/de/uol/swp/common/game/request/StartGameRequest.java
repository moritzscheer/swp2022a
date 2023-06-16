package de.uol.swp.common.game.request;

import de.uol.swp.common.lobby.dto.LobbyDTO;

/**
 * Request sent to the server when the game has been started
 *
 * @see de.uol.swp.common.game.request.StartGameRequest
 * @author Moritz Scheer & Maxim Erden
 * @since 2023-02-28
 */
public class StartGameRequest extends AbstractGameRequest {

    private final int lobbyID;
    private final int numberBots;
    private final int numberCheckpoints;

    /**
     * constructor
     *
     * @param lobby    Integer containing the ID of the lobby
     * @param numberBots
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @since 2022-02-28
     */
    public StartGameRequest(LobbyDTO lobby, int numberBots, int numberCheckpoints) {
        if (lobby == null) {
            throw new NullPointerException("Lobby can not be null");
        }
        if(numberBots < 0) {
            throw new IllegalArgumentException("Number of bots can not be null");
        }
        if(numberCheckpoints < 0) {
            throw new IllegalArgumentException("Number of checkpoints can not be null");
        }
        this.lobbyID = lobby.getLobbyID();
        this.numberBots = numberBots;
        this.numberCheckpoints = numberCheckpoints;
        this.lobby = lobby;


    }

    /**
     * Getter for the lobbyID variable
     *
     * @return Integer containing the Lobby ID
     * @author Maria Eduarda Costa Leite Andrade, WKempel
     * @since 2022-02-28
     */
    public int getLobbyID() {
        return lobbyID;
    }

    public int getNumberBots() {
        return numberBots;
    }

    public int getNumberCheckpoints() {
        return numberCheckpoints;
    }
}
