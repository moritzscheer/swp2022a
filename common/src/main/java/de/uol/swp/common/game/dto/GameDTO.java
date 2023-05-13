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
    private final Integer gameID;

    private List<PlayerDTO> players;

    private BoardDTO boardDTO;

    /**
     * Constructor
     *
     * @param gameID   The id of the card
     * @param players  Players in the lobby
     * @param boardDTO Board of the game
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-02
     */
    public GameDTO(Integer gameID, List<PlayerDTO> players, BoardDTO boardDTO) {
        this.gameID = gameID;
        this.players = players;
        this.boardDTO = boardDTO;
    }

    @Override
    public Integer getGameID() {
        return this.gameID;
    }

    /**
     * Getter for boardDTO
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.dto.BoardDTO
     * @since 2023-05-13
     */
    public BoardDTO getBoardDTO() {
        return boardDTO;
    }

    /**
     * Setter for boardDTO
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.dto.BoardDTO
     * @since 2023-05-13
     */
    public void setBoardDTO(BoardDTO boardDTO) {
        this.boardDTO = boardDTO;
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
