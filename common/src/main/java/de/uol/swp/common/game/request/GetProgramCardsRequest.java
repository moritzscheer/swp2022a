package de.uol.swp.common.game.request;

/**
 * Request sent to the server when the user requests the cards
 *
 * @see de.uol.swp.common.message.AbstractRequestMessage
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-01
 */
public class GetProgramCardsRequest extends AbstractGameRequest {

    private final Integer gameID;
    private final Integer playerID;
    private final Integer robotID;

    /**
     * constructor
     *
     * @param gameID Integer containing the ID of the game
     * @param playerID Integer containing the ID of the player
     * @param robotID Integer containing the ID of the robot
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public GetProgramCardsRequest(Integer gameID, Integer playerID, Integer robotID) {
        this.gameID = gameID;
        this.playerID = playerID;
        this.robotID = robotID;
    }

    /**
     * Getter for the gameID variable
     *
     * @return Integer containing the gameID
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public Integer getGameID() {
        return gameID;
    }

    /**
     * Getter for the playerID variable
     *
     * @return Integer containing the playerID
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public Integer getPlayerID() {
        return playerID;
    }

    /**
     * Getter for the robotID variable
     *
     * @return Integer containing the robotID
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-01
     */
    public Integer getRobotID() {
        return robotID;
    }
}
