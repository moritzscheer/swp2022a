package de.uol.swp.server.gamelogic.unitTest.maps;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Game;
import de.uol.swp.server.gamelogic.map.MapOne;
import de.uol.swp.server.gamelogic.tiles.*;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Test Map One
 *
 * @author Maria
 * @since 2023-07-06
 */
public class MapOneTest {
    private Game game;
    private Block[][] board;

    public Block[][] setGame() {
        List<User> users = new ArrayList<User>();
        users.add(new UserDTO("test1", "test1", ""));
        Set<User> usersSet = new HashSet<>(users);

        game = new Game(1, usersSet, "MapOne", 0, 2, 1);
        return game.getBoard();
    }

    /**
     * Test Map One from MapBuilder, with dynamic lasers
     *
     * @author Maria
     * @since 2023-07-06
     */
    @Test
    public void testMapOneBlocks() {
        board = setGame();

        // Assert the dimensions of the board
        assertEquals(12, board.length);
        assertEquals(12, board[0].length);

        // Row 0
        assertBlockBehaviors(board, 0, 0, RepairBehaviour.class);
        assertNoBehaviors(board, 1, 0);
        assertBlockBehaviors(board, 2, 0, WallBehaviour.class);
        assertBlockBehaviors(board, 3, 0, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 0, WallBehaviour.class);
        assertNoBehaviors(board, 5, 0);
        assertBlockBehaviors(board, 6, 0, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 0, WallBehaviour.class);
        assertNoBehaviors(board, 8, 0);
        assertBlockBehaviors(board, 9, 0, WallBehaviour.class);
        assertBlockBehaviors(board, 10, 0, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 0);

        // Row 1
        assertBlockBehaviors(board, 0, 1, GearBehaviour.class);
        assertBlockBehaviors(board, 1, 1, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 1, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 1, ExpressConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 4, 1);
        assertBlockBehaviors(board, 5, 1, WallBehaviour.class, PusherBehaviour.class);
        assertBlockBehaviors(board, 6, 1, ExpressConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 1);
        assertNoBehaviors(board, 8, 1);
        assertNoBehaviors(board, 9, 1);
        assertBlockBehaviors(board, 10, 1, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 1);

        // Row 2
        assertBlockBehaviors(board, 0, 2, ConveyorBeltBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 1, 2);
        assertNoBehaviors(board, 2, 2);
        assertBlockBehaviors(board, 3, 2, PitBehaviour.class);
        assertNoBehaviors(board, 4, 2);
        assertBlockBehaviors(board, 5, 2, WallBehaviour.class);
        assertBlockBehaviors(board, 6, 2, WallBehaviour.class, PusherBehaviour.class);
        assertNoBehaviors(board, 7, 2);
        assertBlockBehaviors(board, 8, 2, PitBehaviour.class);
        assertBlockBehaviors(board, 9, 2);
        assertBlockBehaviors(board, 10, 2, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 2, WallBehaviour.class);

        // Row 3
        assertBlockBehaviors(board, 0, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 3, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 2, 3);
        assertNoBehaviors(board, 3, 3);
        assertNoBehaviors(board, 4, 3);
        assertNoBehaviors(board, 5, 3);
        assertNoBehaviors(board, 6, 3);
        assertNoBehaviors(board, 7, 3);
        assertNoBehaviors(board, 8, 3);
        assertBlockBehaviors(board, 10, 3, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 3);

        // Row 4
        assertBlockBehaviors(board, 0, 4, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 1, 4, ConveyorBeltBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 2, 4, LaserBehaviour.class);
        assertBlockBehaviors(board, 3, 4, LaserBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 4, 4, WallBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 5, 4);
        assertNoBehaviors(board, 6, 4);
        assertBlockBehaviors(board, 7, 4, WallBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 8, 4, LaserBehaviour.class);
        assertBlockBehaviors(board, 9, 4, LaserBehaviour.class);
        assertBlockBehaviors(board, 10, 4, LaserBehaviour.class);
        assertBlockBehaviors(board, 11, 4, WallBehaviour.class, LaserBehaviour.class);

        // Row 5
        assertNoBehaviors(board, 0, 5);
        assertBlockBehaviors(board, 1, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 5, WallBehaviour.class);
        assertNoBehaviors(board, 3, 5);
        assertNoBehaviors(board, 4, 5);
        assertBlockBehaviors(board, 5, 5, RepairBehaviour.class);
        assertBlockBehaviors(board, 6, 5, RepairBehaviour.class);
        assertNoBehaviors(board, 7, 5);
        assertNoBehaviors(board, 8, 5);
        assertBlockBehaviors(board, 9, 5, WallBehaviour.class, PusherBehaviour.class);
        assertNoBehaviors(board, 10, 5);
        assertNoBehaviors(board, 11, 5);

        // Row 6
        assertNoBehaviors(board, 0, 6);
        assertBlockBehaviors(board, 1, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 6, WallBehaviour.class, PusherBehaviour.class);
        assertNoBehaviors(board, 3, 6);
        assertNoBehaviors(board, 4, 6);
        assertBlockBehaviors(board, 5, 6, RepairBehaviour.class);
        assertBlockBehaviors(board, 6, 6, RepairBehaviour.class);
        assertNoBehaviors(board, 7, 6);
        assertNoBehaviors(board, 8, 6);
        assertBlockBehaviors(board, 9, 6, WallBehaviour.class, PusherBehaviour.class);
        assertBlockBehaviors(board, 10, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 6, ConveyorBeltBehaviour.class);

        // Row 7
        assertBlockBehaviors(board, 0, 7, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 1, 7, ConveyorBeltBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 2, 7, LaserBehaviour.class);
        assertBlockBehaviors(board, 3, 7, LaserBehaviour.class);
        assertBlockBehaviors(board, 4, 7, WallBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 5, 7);
        assertNoBehaviors(board, 6, 7);
        assertBlockBehaviors(board, 7, 7, WallBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 8, 7, LaserBehaviour.class);
        assertBlockBehaviors(board, 9, 7, LaserBehaviour.class);
        assertBlockBehaviors(board, 10, 7, ConveyorBeltBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 11, 7, WallBehaviour.class, LaserBehaviour.class);

        // Row 8
        assertBlockBehaviors(board, 0, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 8, GearBehaviour.class);
        assertBlockBehaviors(board, 2, 8, WallBehaviour.class, LaserBehaviour.class);
        assertNoBehaviors(board, 3, 8);
        assertNoBehaviors(board, 4, 8);
        assertNoBehaviors(board, 5, 8);
        assertNoBehaviors(board, 6, 8);
        assertNoBehaviors(board, 7, 8);
        assertNoBehaviors(board, 8, 8);
        assertNoBehaviors(board, 9, 8);
        assertBlockBehaviors(board, 10, 8, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 8);

        // Row 9
        assertBlockBehaviors(board, 0, 9, WallBehaviour.class);
        assertNoBehaviors(board, 1, 9);
        assertBlockBehaviors(board, 2, 9, LaserBehaviour.class);
        assertBlockBehaviors(board, 3, 9, PitBehaviour.class);
        assertNoBehaviors(board, 4, 9);
        assertBlockBehaviors(board, 5, 9, WallBehaviour.class);
        assertBlockBehaviors(board, 6, 9, WallBehaviour.class, PusherBehaviour.class);
        assertNoBehaviors(board, 7, 9);
        assertBlockBehaviors(board, 8, 9, PitBehaviour.class);
        assertNoBehaviors(board, 9, 9);
        assertBlockBehaviors(board, 10, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 9, WallBehaviour.class);

        // Row 10
        assertBlockBehaviors(board, 0, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 10, LaserBehaviour.class);
        assertNoBehaviors(board, 4, 10);
        assertNoBehaviors(board, 5, 10);
        assertBlockBehaviors(board, 6, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 10, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 10);

        // Row 11
        assertNoBehaviors(board, 0, 11);
        assertBlockBehaviors(board, 1, 11, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 11, WallBehaviour.class, LaserBehaviour.class);
        assertNoBehaviors(board, 3, 11);
        assertNoBehaviors(board, 5, 11);
        assertBlockBehaviors(board, 6, 11, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 11, WallBehaviour.class);
        assertNoBehaviors(board, 8, 11);
        assertBlockBehaviors(board, 9, 11, WallBehaviour.class);
        assertNoBehaviors(board, 10, 11);
        assertBlockBehaviors(board, 11, 11, RepairBehaviour.class);
    }

    /**
     * Test Map One from MapOne, without dynamic lasers and checkpoints
     *
     * @author Maria
     * @since 2023-07-06
     */
    @Test
    public void testMapOne() {
        board = new MapOne().getMap();
        // Assert the dimensions of the board
        assertEquals(12, board.length);
        assertEquals(12, board[0].length);

        // Row 0
        assertBlockBehaviors(board, 0, 0, RepairBehaviour.class);
        assertNoBehaviors(board, 1, 0);
        assertBlockBehaviors(board, 2, 0, WallBehaviour.class);
        assertBlockBehaviors(board, 3, 0, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 0, WallBehaviour.class);
        assertNoBehaviors(board, 5, 0);
        assertBlockBehaviors(board, 6, 0, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 0, WallBehaviour.class);
        assertNoBehaviors(board, 8, 0);
        assertBlockBehaviors(board, 9, 0, WallBehaviour.class);
        assertBlockBehaviors(board, 10, 0, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 0);

        // Row 1
        assertBlockBehaviors(board, 0, 1, GearBehaviour.class);
        assertBlockBehaviors(board, 1, 1, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 1, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 1, ExpressConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 4, 1);
        assertBlockBehaviors(board, 5, 1, WallBehaviour.class, PusherBehaviour.class);
        assertBlockBehaviors(board, 6, 1, ExpressConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 1);
        assertNoBehaviors(board, 8, 1);
        assertNoBehaviors(board, 9, 1);
        assertBlockBehaviors(board, 10, 1, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 1);

        // Row 2
        assertBlockBehaviors(board, 0, 2, ConveyorBeltBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 1, 2);
        assertNoBehaviors(board, 2, 2);
        assertBlockBehaviors(board, 3, 2, PitBehaviour.class);
        assertNoBehaviors(board, 4, 2);
        assertBlockBehaviors(board, 5, 2, WallBehaviour.class);
        assertBlockBehaviors(board, 6, 2, WallBehaviour.class, PusherBehaviour.class);
        assertNoBehaviors(board, 7, 2);
        assertBlockBehaviors(board, 8, 2, PitBehaviour.class);
        assertBlockBehaviors(board, 9, 2);
        assertBlockBehaviors(board, 10, 2, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 2, WallBehaviour.class);

        // Row 3
        assertBlockBehaviors(board, 0, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 3, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 2, 3);
        assertNoBehaviors(board, 3, 3);
        assertNoBehaviors(board, 4, 3);
        assertNoBehaviors(board, 5, 3);
        assertNoBehaviors(board, 6, 3);
        assertNoBehaviors(board, 7, 3);
        assertNoBehaviors(board, 8, 3);
        assertBlockBehaviors(board, 10, 3, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 3);

        // Row 4
        assertBlockBehaviors(board, 0, 4, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 1, 4, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 2, 4);
        assertBlockBehaviors(board, 3, 4, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 4, 4, WallBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 5, 4);
        assertNoBehaviors(board, 6, 4);
        assertBlockBehaviors(board, 7, 4, WallBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 8, 4);
        assertNoBehaviors(board, 9, 4);
        assertNoBehaviors(board, 10, 4);
        assertBlockBehaviors(board, 11, 4, WallBehaviour.class, LaserBehaviour.class);

        // Row 5
        assertNoBehaviors(board, 0, 5);
        assertBlockBehaviors(board, 1, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 5, WallBehaviour.class);
        assertNoBehaviors(board, 3, 5);
        assertNoBehaviors(board, 4, 5);
        assertBlockBehaviors(board, 5, 5, RepairBehaviour.class);
        assertBlockBehaviors(board, 6, 5, RepairBehaviour.class);
        assertNoBehaviors(board, 7, 5);
        assertNoBehaviors(board, 8, 5);
        assertBlockBehaviors(board, 9, 5, WallBehaviour.class, PusherBehaviour.class);
        assertNoBehaviors(board, 10, 5);
        assertNoBehaviors(board, 11, 5);

        // Row 6
        assertNoBehaviors(board, 0, 6);
        assertBlockBehaviors(board, 1, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 6, WallBehaviour.class, PusherBehaviour.class);
        assertNoBehaviors(board, 3, 6);
        assertNoBehaviors(board, 4, 6);
        assertBlockBehaviors(board, 5, 6, RepairBehaviour.class);
        assertBlockBehaviors(board, 6, 6, RepairBehaviour.class);
        assertNoBehaviors(board, 7, 6);
        assertNoBehaviors(board, 8, 6);
        assertBlockBehaviors(board, 9, 6, WallBehaviour.class, PusherBehaviour.class);
        assertBlockBehaviors(board, 10, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 6, ConveyorBeltBehaviour.class);

        // Row 7
        assertBlockBehaviors(board, 0, 7, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 1, 7, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 2, 7);
        assertNoBehaviors(board, 3, 7);
        assertBlockBehaviors(board, 4, 7, WallBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 5, 7);
        assertNoBehaviors(board, 6, 7);
        assertBlockBehaviors(board, 7, 7, WallBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 8, 7);
        assertNoBehaviors(board, 9, 7);
        assertBlockBehaviors(board, 10, 7, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 7, WallBehaviour.class, LaserBehaviour.class);

        // Row 8
        assertBlockBehaviors(board, 0, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 8, GearBehaviour.class);
        assertBlockBehaviors(board, 2, 8, WallBehaviour.class);
        assertNoBehaviors(board, 3, 8);
        assertNoBehaviors(board, 4, 8);
        assertNoBehaviors(board, 5, 8);
        assertNoBehaviors(board, 6, 8);
        assertNoBehaviors(board, 7, 8);
        assertNoBehaviors(board, 8, 8);
        assertNoBehaviors(board, 9, 8);
        assertBlockBehaviors(board, 10, 8, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 8);

        // Row 9
        assertBlockBehaviors(board, 0, 9, WallBehaviour.class);
        assertNoBehaviors(board, 1, 9);
        assertNoBehaviors(board, 2, 9);
        assertBlockBehaviors(board, 3, 9, PitBehaviour.class);
        assertNoBehaviors(board, 4, 9);
        assertBlockBehaviors(board, 5, 9, WallBehaviour.class);
        assertBlockBehaviors(board, 6, 9, WallBehaviour.class, PusherBehaviour.class);
        assertNoBehaviors(board, 7, 9);
        assertBlockBehaviors(board, 8, 9, PitBehaviour.class);
        assertNoBehaviors(board, 9, 9);
        assertBlockBehaviors(board, 10, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 9, WallBehaviour.class);

        // Row 10
        assertBlockBehaviors(board, 0, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 10, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 2, 10);
        assertNoBehaviors(board, 4, 10);
        assertNoBehaviors(board, 5, 10);
        assertBlockBehaviors(board, 6, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 10, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 10);

        // Row 11
        assertNoBehaviors(board, 0, 11);
        assertBlockBehaviors(board, 1, 11, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 11, WallBehaviour.class, LaserBehaviour.class);
        assertNoBehaviors(board, 3, 11);
        assertNoBehaviors(board, 5, 11);
        assertBlockBehaviors(board, 6, 11, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 11, WallBehaviour.class);
        assertNoBehaviors(board, 8, 11);
        assertBlockBehaviors(board, 9, 11, WallBehaviour.class);
        assertNoBehaviors(board, 10, 11);
        assertBlockBehaviors(board, 11, 11, RepairBehaviour.class);
    }

    private void assertBlockBehaviors(
            Block[][] board, int x, int y, Class<?>... expectedBehaviors) {
        assertEquals(expectedBehaviors.length, board[x][y].getBehaviourList().length);
        for (Class<?> behavior : expectedBehaviors) {
            assertTrue(
                    Arrays.stream(board[x][y].getBehaviourList())
                            .anyMatch(
                                    beh ->
                                            behavior.getSimpleName()
                                                    .equals(beh.getClass().getSimpleName())));
        }
    }

    private void assertNoBehaviors(Block[][] board, int x, int y) {
        assertEquals(0, board[x][y].getBehaviourList().length);
    }
}
