package de.uol.swp.server.gamelogic.unitTest.maps;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Game;
import de.uol.swp.server.gamelogic.map.MapTwo;
import de.uol.swp.server.gamelogic.tiles.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test Map Two
 *
 * @author Maria
 * @since 2023-07-06
 */
@DisplayName("MapTwoTest")
public class MapTwoTest {
    private Game game;
    private Block[][] board;

    public Block[][] setGame() {
        List<User> users = new ArrayList<User>();
        users.add(new UserDTO("test1", "test1", ""));
        Set<User> usersSet = new HashSet<>(users);

        game = new Game(1, usersSet, "MapTwo", 0, 2, 1);
        return game.getBoard();
    }

    /**
     * Test Map Two from MapBuilder, with dynamic lasers
     *
     * @author Maria
     * @since 2023-07-06
     */
    @Test
    public void testMapTwoBlocks() {
        board = setGame();

        // Assert the dimensions of the board
        assertEquals(12, board.length);
        assertEquals(12, board[0].length);

        // Row 0
        assertBlockBehaviors(board, 0, 0, RepairBehaviour.class);
        assertNoBehaviors(board, 1, 0);
        assertBlockBehaviors(board, 2, 0, WallBehaviour.class);
        assertBlockBehaviors(board, 3, 0, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 0, WallBehaviour.class);
        assertBlockBehaviors(board, 5, 0, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 0, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 0, WallBehaviour.class);
        assertBlockBehaviors(board, 8, 0, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 0, WallBehaviour.class);
        assertBlockBehaviors(board, 10, 0, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 0);

        // Row 1
        assertBlockBehaviors(board, 0, 1, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 1, 1);
        assertBlockBehaviors(board, 2, 1, PitBehaviour.class);
        assertBlockBehaviors(board, 3, 1, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 4, 1);
        assertBlockBehaviors(board, 5, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 1, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 1);
        assertBlockBehaviors(board, 8, 1, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 9, 1);
        assertBlockBehaviors(board, 10, 1, GearBehaviour.class);
        assertBlockBehaviors(board, 11, 1, ConveyorBeltBehaviour.class);

        // Row 2
        assertBlockBehaviors(board, 0, 2, WallBehaviour.class);
        assertNoBehaviors(board, 1, 2);
        assertNoBehaviors(board, 2, 2);
        assertBlockBehaviors(board, 3, 2, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 4, 2);
        assertBlockBehaviors(board, 5, 2, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 2, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 2);
        assertBlockBehaviors(board, 8, 2, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 2, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 10, 2, LaserBehaviour.class);
        assertBlockBehaviors(board, 11, 2, WallBehaviour.class, LaserBehaviour.class);

        // Row 3
        assertBlockBehaviors(board, 0, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 3, GearBehaviour.class);
        assertNoBehaviors(board, 4, 3);
        assertBlockBehaviors(board, 5, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 3, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 3);
        assertBlockBehaviors(board, 8, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 3, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 3, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 3, ExpressConveyorBeltBehaviour.class);

        // Row 4
        assertBlockBehaviors(board, 0, 4, WallBehaviour.class);
        assertNoBehaviors(board, 1, 4);
        assertNoBehaviors(board, 3, 4);
        assertBlockBehaviors(board, 4, 4, WallBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 5, 4, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 4, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 4, WallBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 8, 4);
        assertNoBehaviors(board, 10, 4);
        assertBlockBehaviors(board, 11, 4, WallBehaviour.class);

        // Row 5
        assertBlockBehaviors(board, 0, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 5, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 5, 5);
        assertNoBehaviors(board, 6, 5);
        assertBlockBehaviors(board, 7, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 5, ConveyorBeltBehaviour.class);

        // Row 6
        assertNoBehaviors(board, 0, 6);
        assertBlockBehaviors(board, 1, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 6, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 5, 6);
        assertNoBehaviors(board, 6, 6);
        assertBlockBehaviors(board, 7, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 6, ExpressConveyorBeltBehaviour.class);

        // Row 7
        assertBlockBehaviors(board, 0, 7, WallBehaviour.class);
        assertNoBehaviors(board, 1, 7);
        assertNoBehaviors(board, 2, 7);
        assertNoBehaviors(board, 3, 7);
        assertBlockBehaviors(board, 4, 7, WallBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 5, 7, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 7, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 7, WallBehaviour.class, WallBehaviour.class, RepairBehaviour.class);
        assertNoBehaviors(board, 8, 7);
        assertNoBehaviors(board, 9, 7);
        assertNoBehaviors(board, 10, 7);
        assertBlockBehaviors(board, 11, 7, WallBehaviour.class);

        // Row 8
        assertBlockBehaviors(board, 0, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 8, GearBehaviour.class);
        assertNoBehaviors(board, 4, 8);
        assertBlockBehaviors(board, 5, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 8, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 8);
        assertBlockBehaviors(board, 8, 8, GearBehaviour.class);
        assertBlockBehaviors(board, 9, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 8, ConveyorBeltBehaviour.class);

        // Row 9
        assertBlockBehaviors(board, 0, 9, WallBehaviour.class);
        assertNoBehaviors(board, 1, 9);
        assertNoBehaviors(board, 2, 9);
        assertBlockBehaviors(board, 3, 9, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 4, 9);
        assertBlockBehaviors(board, 5, 9, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 9, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 9);
        assertBlockBehaviors(board, 8, 9, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 9, 9);
        assertBlockBehaviors(board, 10, 9, WallBehaviour.class);
        assertBlockBehaviors(board, 11, 9, WallBehaviour.class);

        // Row 10
        assertBlockBehaviors(board, 0, 10, PitBehaviour.class);
        assertBlockBehaviors(board, 1, 10, WallBehaviour.class);
        assertBlockBehaviors(board, 2, 10, WallBehaviour.class);
        assertBlockBehaviors(board, 3, 10, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 4, 10);
        assertBlockBehaviors(board, 5, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 10, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 10);
        assertBlockBehaviors(board, 8, 10, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 9, 10);
        assertBlockBehaviors(board, 10, 10, GearBehaviour.class);
        assertBlockBehaviors(board, 11, 10, ConveyorBeltBehaviour.class);

        // Row 11
        assertNoBehaviors(board, 0, 11);
        assertNoBehaviors(board, 1, 11);
        assertBlockBehaviors(board, 2, 11, WallBehaviour.class);
        assertBlockBehaviors(board, 3, 11, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 11, WallBehaviour.class);
        assertBlockBehaviors(board, 5, 11, ExpressConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 6, 11);
        assertBlockBehaviors(board, 7, 11, WallBehaviour.class);
        assertBlockBehaviors(board, 8, 11, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 11, WallBehaviour.class);
        assertBlockBehaviors(board, 10, 11, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 11, RepairBehaviour.class);

    }

    /**
     * Test Map Two from MapOne, without dynamic lasers and checkpoints
     *
     * @author Maria
     * @since 2023-07-06
     */
    @Test
    public void testMapTwo() {
        board = new MapTwo().getMap();

        // Assert the dimensions of the board
        assertEquals(12, board.length);
        assertEquals(12, board[0].length);

        // Row 0
        assertBlockBehaviors(board, 0, 0, RepairBehaviour.class);
        assertNoBehaviors(board, 1, 0);
        assertBlockBehaviors(board, 2, 0, WallBehaviour.class);
        assertBlockBehaviors(board, 3, 0, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 0, WallBehaviour.class);
        assertBlockBehaviors(board, 5, 0, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 0, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 0, WallBehaviour.class);
        assertBlockBehaviors(board, 8, 0, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 0, WallBehaviour.class);
        assertBlockBehaviors(board, 10, 0, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 0);

        // Row 1
        assertBlockBehaviors(board, 0, 1, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 1, 1);
        assertBlockBehaviors(board, 2, 1, PitBehaviour.class);
        assertBlockBehaviors(board, 3, 1, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 4, 1);
        assertBlockBehaviors(board, 5, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 1, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 1);
        assertBlockBehaviors(board, 8, 1, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 9, 1);
        assertBlockBehaviors(board, 10, 1, GearBehaviour.class);
        assertBlockBehaviors(board, 11, 1, ConveyorBeltBehaviour.class);

        // Row 2
        assertBlockBehaviors(board, 0, 2, WallBehaviour.class);
        assertNoBehaviors(board, 1, 2);
        assertNoBehaviors(board, 2, 2);
        assertBlockBehaviors(board, 3, 2, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 4, 2);
        assertBlockBehaviors(board, 5, 2, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 2, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 2);
        assertBlockBehaviors(board, 8, 2, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 2, WallBehaviour.class);
        assertNoBehaviors(board, 10, 2);
        assertBlockBehaviors(board, 11, 2, WallBehaviour.class, LaserBehaviour.class);

        // Row 3
        assertBlockBehaviors(board, 0, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 3, GearBehaviour.class);
        assertNoBehaviors(board, 4, 3);
        assertBlockBehaviors(board, 5, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 3, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 3);
        assertBlockBehaviors(board, 8, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 3, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 3, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 3, ExpressConveyorBeltBehaviour.class);

        // Row 4
        assertBlockBehaviors(board, 0, 4, WallBehaviour.class);
        assertNoBehaviors(board, 1, 4);
        assertNoBehaviors(board, 3, 4);
        assertBlockBehaviors(board, 4, 4, WallBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 5, 4, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 4, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 4, WallBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 8, 4);
        assertNoBehaviors(board, 10, 4);
        assertBlockBehaviors(board, 11, 4, WallBehaviour.class);

        // Row 5
        assertBlockBehaviors(board, 0, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 5, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 5, 5);
        assertNoBehaviors(board, 6, 5);
        assertBlockBehaviors(board, 7, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 5, ConveyorBeltBehaviour.class);

        // Row 6
        assertNoBehaviors(board, 0, 6);
        assertBlockBehaviors(board, 1, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 6, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 5, 6);
        assertNoBehaviors(board, 6, 6);
        assertBlockBehaviors(board, 7, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 6, ExpressConveyorBeltBehaviour.class);

        // Row 7
        assertBlockBehaviors(board, 0, 7, WallBehaviour.class);
        assertNoBehaviors(board, 1, 7);
        assertNoBehaviors(board, 2, 7);
        assertNoBehaviors(board, 3, 7);
        assertBlockBehaviors(board, 4, 7, WallBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 5, 7, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 7, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 7, WallBehaviour.class, WallBehaviour.class, RepairBehaviour.class);
        assertNoBehaviors(board, 8, 7);
        assertNoBehaviors(board, 9, 7);
        assertNoBehaviors(board, 10, 7);
        assertBlockBehaviors(board, 11, 7, WallBehaviour.class);

        // Row 8
        assertBlockBehaviors(board, 0, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 8, GearBehaviour.class);
        assertNoBehaviors(board, 4, 8);
        assertBlockBehaviors(board, 5, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 8, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 8);
        assertBlockBehaviors(board, 8, 8, GearBehaviour.class);
        assertBlockBehaviors(board, 9, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 8, ConveyorBeltBehaviour.class);

        // Row 9
        assertBlockBehaviors(board, 0, 9, WallBehaviour.class);
        assertNoBehaviors(board, 1, 9);
        assertNoBehaviors(board, 2, 9);
        assertBlockBehaviors(board, 3, 9, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 4, 9);
        assertBlockBehaviors(board, 5, 9, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 9, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 9);
        assertBlockBehaviors(board, 8, 9, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 9, 9);
        assertBlockBehaviors(board, 10, 9, WallBehaviour.class);
        assertBlockBehaviors(board, 11, 9, WallBehaviour.class);

        // Row 10
        assertBlockBehaviors(board, 0, 10, PitBehaviour.class);
        assertBlockBehaviors(board, 1, 10, WallBehaviour.class);
        assertBlockBehaviors(board, 2, 10, WallBehaviour.class);
        assertBlockBehaviors(board, 3, 10, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 4, 10);
        assertBlockBehaviors(board, 5, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 10, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 7, 10);
        assertBlockBehaviors(board, 8, 10, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 9, 10);
        assertBlockBehaviors(board, 10, 10, GearBehaviour.class);
        assertBlockBehaviors(board, 11, 10, ConveyorBeltBehaviour.class);

        // Row 11
        assertNoBehaviors(board, 0, 11);
        assertNoBehaviors(board, 1, 11);
        assertBlockBehaviors(board, 2, 11, WallBehaviour.class);
        assertBlockBehaviors(board, 3, 11, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 11, WallBehaviour.class);
        assertBlockBehaviors(board, 5, 11, ExpressConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 6, 11);
        assertBlockBehaviors(board, 7, 11, WallBehaviour.class);
        assertBlockBehaviors(board, 8, 11, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 11, WallBehaviour.class);
        assertBlockBehaviors(board, 10, 11, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 11, RepairBehaviour.class);

    }

    private void assertBlockBehaviors(Block[][] board, int x, int y, Class<?>... expectedBehaviors) {
        assertEquals(expectedBehaviors.length, board[x][y].getBehaviourList().length);
        for (Class<?> behavior : expectedBehaviors) {
            assertTrue(Arrays.stream(board[x][y].getBehaviourList())
                    .anyMatch(beh -> behavior.getSimpleName().equals(beh.getClass().getSimpleName())));
        }
    }

    private void assertNoBehaviors(Block[][] board, int x, int y) {
        assertEquals(0, board[x][y].getBehaviourList().length);
    }
}
