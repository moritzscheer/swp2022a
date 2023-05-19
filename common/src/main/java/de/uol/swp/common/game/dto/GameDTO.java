package de.uol.swp.common.game.dto;

import de.uol.swp.common.game.Game;

import java.util.List;

/**
 * Object to transfer the information of a Game
 *
 * This object is used to communicate the current state of game between the server and
 * clients. It contains information about the gameID, Board state and Players list
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-02
 */
public class GameDTO implements Game {
    private List<PlayerDTO> players;

    /**
     * Constructor
     *
     * @param players  Players in the lobby
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-02
     */
    public GameDTO(List<PlayerDTO> players) {
        this.players = players;
    }

    /**
     * Getter for list of Players
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-05-13
     */
    public List<PlayerDTO> getPlayers() {
        return players;
    }

    /**
     * Setter for list of Players
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.dto.PlayerDTO
     * @since 2023-05-13
     */
    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }
}
