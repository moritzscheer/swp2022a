package de.uol.swp.common.game.dto;

import de.uol.swp.common.game.Game;

// TODO
public class GameDTO implements Game {
    private final Integer gameID;

    /**
     * Constructor
     *
     * @param gameID The id of the card
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-02
     */
    public GameDTO(Integer gameID) {
        this.gameID = gameID;
    }

    @Override
    public Integer getGameID() {
        return this.gameID;
    }

}
