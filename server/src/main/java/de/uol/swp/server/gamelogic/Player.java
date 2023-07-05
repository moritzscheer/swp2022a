package de.uol.swp.server.gamelogic;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.UserDTO;

/**
 * @author Maria Eduarda Costa Leite Andrade
 * @see de.uol.swp.server.gamelogic.cards.Card
 * @see de.uol.swp.server.gamelogic.Robot
 * @since 2023-03-28
 */
public class Player extends AbstractPlayer {

    /**
     * Constructor
     *
     * @param robotID the ID of the robot
     * @param startPosition the start position of the robot
     * @param userDTO the userDTO of the player
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-03-28
     */
    public Player(UserDTO userDTO, Position startPosition, int robotID) {
        super();
        this.userDTO = userDTO;
        // TODO: we might need to change the robot constructor
        this.robot = new Robot(robotID, startPosition, CardinalDirection.North);
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-03-28
     */
    public void setUser(UserDTO user) {
        this.userDTO = user;
    }
}
