package de.uol.swp.server.gamelogic.unitTest.moves;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.moves.GameMovement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GameMovementTest {

    private GameMovement gameMovement;
    private List<PlayerDTO> robotsPositionsInOneMove = new ArrayList<>();
    private String moveType;
    private GameMovement oldState;
    private final Position position = new Position(1, 2);
    private final CardinalDirection direction = CardinalDirection.North;
    private String robotThatPlayedTheCard;
    private final RobotDTO robotDTO = new RobotDTO(123, position, direction);
    private final UserDTO userDTO = new UserDTO("Player1", "pw", "email");
    private final PlayerDTO playerDTO = new PlayerDTO(robotDTO, userDTO);

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-21
     */
    @BeforeEach
    public void setUp() {
        robotsPositionsInOneMove.add(playerDTO);

        moveType = "Move";
        oldState = new GameMovement(new ArrayList<>(), "", null, "");
        robotThatPlayedTheCard = "Player1";

        gameMovement =
                new GameMovement(
                        robotsPositionsInOneMove, moveType, oldState, robotThatPlayedTheCard);
    }

    /**
     * Tests getRobotsPositionsInOneMove method
     *
     * @author WKempel
     * @result The getRobotsPositionsInOneMove method should return true if the
     *     robotsPositionsInOneMove are correct
     * @see de.uol.swp.server.gamelogic.moves.GameMovement
     * @since 2023-06-21
     */
    @Test
    public void testGetRobotsPositionsInOneMove() {
        Assertions.assertEquals(
                robotsPositionsInOneMove, gameMovement.getRobotsPositionsInOneMove());
    }

    /**
     * Tests getMoveType method
     *
     * @author WKempel
     * @result The getMoveType method should return true if the moveType is correct
     * @see de.uol.swp.server.gamelogic.moves.GameMovement
     * @since 2023-06-21
     */
    @Test
    public void testGetMoveMessage() {
        String expectedMoveMessage = "[" + moveType + "] " + robotThatPlayedTheCard + "\n";
        Assertions.assertEquals(expectedMoveMessage, gameMovement.getMoveMessage());
    }
}
