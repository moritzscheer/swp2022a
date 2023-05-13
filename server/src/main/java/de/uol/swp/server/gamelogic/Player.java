package de.uol.swp.server.gamelogic;

import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.game.enums.CardinalDirection;


/**
 * @author Maria Eduarda Costa Leite Andrade
 * @see de.uol.swp.server.gamelogic.cards.Card
 * @see de.uol.swp.server.gamelogic.Robot
 * @since 2023-03-28
 */
public class Player extends AbstractPlayer {
    private UserDTO userDTO;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 2023-03-28
     */
    public Player(UserDTO userDTO, Position startPosition) {
        super();
        this.userDTO = userDTO;
        // TODO: we might need to change the robot constructor
        this.robot = new Robot("", startPosition, true, CardinalDirection.East);
    }

    public UserDTO getUser() {
        return userDTO;
    }

    public void setUser(UserDTO user) {
        this.userDTO = user;
    }

}
