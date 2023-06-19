package de.uol.swp.server.gamelogic.unitTest.tiles;

import static org.junit.jupiter.api.Assertions.*;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.MoveIntent;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;
import de.uol.swp.server.gamelogic.tiles.PusherBehaviour;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Test PusherBehaviour
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-03-05
 */
public class PusherBehaviourTest {

    private static final Position pos1 = new Position(1, 1);
    private static final Position pos2 = new Position(1, 2);
    private static final Robot[] robots = new Robot[1];
    private static final AbstractTileBehaviour[] behaviours1 = new AbstractTileBehaviour[1];
    private static final Block[][] board = new Block[1][2];

    int[] activeInProgramSteps = {1, 3};
    CardinalDirection direction = CardinalDirection.West; // means it will push east
    int programStep = 1;

    @Before
    public void setup() throws Exception {
        robots[0] = new Robot(1, pos1, true, CardinalDirection.East);
        behaviours1[0] =
                new PusherBehaviour(List.of(robots), board, pos1, activeInProgramSteps, direction);
        board[0][0] = new Block(behaviours1, "", pos1);
        board[0][1] = new Block("", pos2);
    }

    /**
     * Test push robot west
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.PusherBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-03-05
     */
    @Test
    public void pushRobotWestTest() {
        // robot in block 0,0 to be pushed
        // program step is 1
        assertEquals(CardinalDirection.West, ((PusherBehaviour) behaviours1[0]).getDirection());
        assertEquals(CardinalDirection.East, ((PusherBehaviour) behaviours1[0]).getPushingDirection());
        // Pushes robot to west
        ((PusherBehaviour) behaviours1[0]).onPusherStage(1);

        // solve move intentions
        List<MoveIntent> moves;
        moves = board[0][0].OnPusherStage(1);
        assertEquals(moves.get(0).getDirection(), ((PusherBehaviour) behaviours1[0]).getPushingDirection());
    }

    /**
     * Test try to push robot west, but pusher is not active
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see CardinalDirection
     * @see de.uol.swp.server.gamelogic.tiles.PusherBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-03-05
     */
    @Test
    public void dontPushRobotWestTest() {
        // TODO must be tested together with move intent
    }
}
