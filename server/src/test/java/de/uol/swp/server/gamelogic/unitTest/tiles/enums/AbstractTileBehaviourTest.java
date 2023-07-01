package de.uol.swp.server.gamelogic.unitTest.tiles.enums;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.moves.MoveIntent;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class AbstractTileBehaviourTest {

    private AbstractTileBehaviour tileBehaviour;
    private List<Robot> robotStates;
    private Block[][] board;
    private Position blockPos;

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-21
     */
    @BeforeEach
    public void setUp() {
        robotStates = new ArrayList<>();
        board = new Block[12][12];
        blockPos = new Position(0, 0);
        tileBehaviour = new AbstractTileBehaviourImpl(robotStates, board, blockPos);
    }

    /**
     * Tests getBlocksPos method
     *
     * @author WKempel
     * @result The getBlocksPos method should return true if the blockPos is correct
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testGetBlockPos() {
        Assertions.assertEquals(blockPos, tileBehaviour.getBlockPos());
    }

    /**
     * Tests onRobotEntered method
     *
     * @author WKempel
     * @result The onRobotEntered method should return null
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testOnRobotEntered() {
        List<MoveIntent> moveIntents = tileBehaviour.onRobotEntered(0);
        Assertions.assertNull(moveIntents);
    }

    /**
     * Tests the onExpressConveyorStage method
     *
     * @author WKempel
     * @result The onExpressConveyorStage method should return null
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testOnExpressConveyorStage() {
        List<MoveIntent> moveIntents = tileBehaviour.onExpressConveyorStage(1);
        Assertions.assertNull(moveIntents);
    }

    /**
     * Tests the onConveyorStage method
     *
     * @author WKempel
     * @result The onConveyorStage method should return null
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testOnConveyorStage() {
        List<MoveIntent> moveIntents = tileBehaviour.onConveyorStage(2);
        Assertions.assertNull(moveIntents);
    }

    /**
     * Tests the onLaserStage method
     *
     * @author WKempel
     * @result The onLaserStage method should return null
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testOnLaserStage() {
        List<MoveIntent> moveIntents = tileBehaviour.onLaserStage(3);
        Assertions.assertNull(moveIntents);
    }

    /**
     * Tests the onPusherStage method
     *
     * @author WKempel
     * @result The onPusherStage method should return null
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testOnPusherStage() {
        List<MoveIntent> moveIntents = tileBehaviour.onPusherStage(4);
        Assertions.assertNull(moveIntents);
    }

    /**
     * Tests the onPressorStage method
     *
     * @author WKempel
     * @result The onPressorStage method should return null
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testOnPressorStage() {
        List<MoveIntent> moveIntents = tileBehaviour.onPressorStage(5);
        Assertions.assertNull(moveIntents);
    }

    /**
     * Tests the getImage method
     *
     * @author WKempel
     * @result The getImage method should return true if the image is empty
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testGetImage() {
        List<int[]> image = tileBehaviour.getImage();
        Assertions.assertTrue(image.isEmpty());
    }

    /**
     * Tests the getObstruction method
     *
     * @author WKempel
     * @result The getObstruction method should return false because there is no obstruction
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testGetObstruction() {
        Assertions.assertFalse(tileBehaviour.getObstruction(CardinalDirection.North));
        Assertions.assertFalse(tileBehaviour.getObstruction(CardinalDirection.South));
        Assertions.assertFalse(tileBehaviour.getObstruction(CardinalDirection.East));
        Assertions.assertFalse(tileBehaviour.getObstruction(CardinalDirection.West));
    }

    /**
     * Tests the onCardEnding method
     *
     * @author WKempel
     * @result The onCardEnding method should return null
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testOnCardEnding() {
        List<MoveIntent> moveIntents = tileBehaviour.onCardEnding(6);
        Assertions.assertNull(moveIntents);
    }

    /**
     * Tests the onRotatorStage method
     *
     * @author WKempel
     * @result The onRotatorStage method should return null
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testOnRotatorStage() {
        List<MoveIntent> moveIntents = tileBehaviour.onRotatorStage(7);
        Assertions.assertNull(moveIntents);
    }

    /**
     * Tests the onCheckPointStage method
     *
     * @author WKempel
     * @result The onCheckPointStage method should return null
     * @see de.uol.swp.server.gamelogic.tiles.enums
     * @since 2023-06-21
     */
    @Test
    public void testOnCheckPointStage() {
        List<MoveIntent> moveIntents = tileBehaviour.onCheckPointStage(8);
        Assertions.assertNull(moveIntents);
    }

    private static class AbstractTileBehaviourImpl extends AbstractTileBehaviour {
        public AbstractTileBehaviourImpl(List<Robot> robotStates, Block[][] board, Position blockPos) {
            super(robotStates, board, blockPos);
        }
    }
}
