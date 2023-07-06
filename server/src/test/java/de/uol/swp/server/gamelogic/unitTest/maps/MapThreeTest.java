package de.uol.swp.server.gamelogic.unitTest.maps;

import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Game;
import de.uol.swp.server.gamelogic.map.MapThree;
import de.uol.swp.server.gamelogic.tiles.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


/**
 * Test Map Three
 *
 * @author Maria
 * @since 2023-07-06
 */
public class MapThreeTest {
    private Block[][] board;

    @BeforeEach
    public void setUp() {
        List<User> users = new ArrayList<User>();
        users.add(new UserDTO("test1", "test1", ""));
        Set<User> usersSet = new HashSet<>(users);

        Game game = new Game(1, usersSet, "MapThree", 0, 2, 1);

        board = game.getBoard();
    }

    /**
     * Test Map Three from MapBuilder, with dynamic lasers
     *
     * @author Maria
     * @since 2023-07-06
     */
    @Test
    public void testMapThreeBlocks() {
        // Assert the dimensions of the board
        assertEquals(12, board.length);
        assertEquals(12, board[0].length);

        // Row 0
        assertNoBehaviors(board, 0, 0);
        assertBlockBehaviors(board, 1, 0, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 0, PusherBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 3, 0);
        assertBlockBehaviors(board, 4, 0, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 5, 0, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 6, 0);
        assertBlockBehaviors(board, 7, 0, PusherBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 8, 0);
        assertBlockBehaviors(board, 9, 0, PusherBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 10, 0);
        assertBlockBehaviors(board, 11, 0, RepairBehaviour.class);

        // Row 1
        assertNoBehaviors(board, 0, 1);
        assertBlockBehaviors(board, 1, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 1, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 1, ExpressConveyorBeltBehaviour.class);

        // Row 2
        assertBlockBehaviors(board, 0, 2, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 1, 2, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 2, CheckPointBehaviour.class);
        assertBlockBehaviors(board, 3, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 2, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 2, PusherBehaviour.class, WallBehaviour.class);

        // Row 3
        assertBlockBehaviors(board, 0, 3);
        assertBlockBehaviors(board, 1, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 3, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 3, ConveyorBeltBehaviour.class, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 6, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 3, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 3, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 3);

        // Row 4
        assertBlockBehaviors(board, 0, 4, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 1, 4, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 4, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 4, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 4, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 4, ExpressConveyorBeltBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 6, 4, ExpressConveyorBeltBehaviour.class, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 7, 4, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 4, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 4, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 4, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 4, PusherBehaviour.class, WallBehaviour.class);

        // Row 5
        assertBlockBehaviors(board, 0, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 5, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 5, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 5, PitBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 6, 5, PitBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 7, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 5, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 5, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 5, ExpressConveyorBeltBehaviour.class);

        // Row 6
        assertBlockBehaviors(board, 0, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 6, ConveyorBeltBehaviour.class, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 4, 6, ExpressConveyorBeltBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 5, 6, PitBehaviour.class, LaserBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 6, 6, PitBehaviour.class, LaserBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 7, 6, ConveyorBeltBehaviour.class, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 8, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 6, ExpressConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 6);

        // Row 7
        assertBlockBehaviors(board, 0, 7, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 1, 7, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 7, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 7, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 7, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 7, ConveyorBeltBehaviour.class, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 6, 7, ConveyorBeltBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 7, 7, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 7, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 7, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 7, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 7, PusherBehaviour.class, WallBehaviour.class);

        // Row 8
        assertBlockBehaviors(board, 0, 8);
        assertBlockBehaviors(board, 1, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 8, ExpressConveyorBeltBehaviour.class, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 7, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 8);

        // Row 9
        assertBlockBehaviors(board, 0, 9, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 1, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 9, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 9, CheckPointBehaviour.class);
        assertBlockBehaviors(board, 10, 9, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 9, PusherBehaviour.class, WallBehaviour.class);

        // Row 10
        assertBlockBehaviors(board, 0, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 10, ExpressConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 10);

        // Row 11
        assertBlockBehaviors(board, 0, 11, RepairBehaviour.class);
        assertNoBehaviors(board, 1, 11);
        assertBlockBehaviors(board, 2, 11, PusherBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 3, 11);
        assertBlockBehaviors(board, 4, 11, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 5, 11, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 11, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 11, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 8, 11, RepairBehaviour.class);
        assertBlockBehaviors(board, 9, 11, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 10, 11, ExpressConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 11);
    }

    /**
     * Test Map Three from MapOne, without dynamic lasers and checkpoints
     *
     * @author Maria
     * @since 2023-07-06
     */
    @Test
    public void testMapThree() {

        board = new MapThree().getMap();
        // Assert the dimensions of the board
        assertEquals(12, board.length);
        assertEquals(12, board[0].length);

        // Row 0
        assertNoBehaviors(board, 0, 0);
        assertBlockBehaviors(board, 1, 0, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 0, PusherBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 3, 0);
        assertBlockBehaviors(board, 4, 0, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 5, 0, ConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 6, 0);
        assertBlockBehaviors(board, 7, 0, PusherBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 8, 0);
        assertBlockBehaviors(board, 9, 0, PusherBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 10, 0);
        assertBlockBehaviors(board, 11, 0, RepairBehaviour.class);

        // Row 1
        assertNoBehaviors(board, 0, 1);
        assertBlockBehaviors(board, 1, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 1, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 1, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 1, ExpressConveyorBeltBehaviour.class);

        // Row 2
        assertBlockBehaviors(board, 0, 2, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 1, 2, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 2, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 2, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 2, PusherBehaviour.class, WallBehaviour.class);

        // Row 3
        assertBlockBehaviors(board, 0, 3);
        assertBlockBehaviors(board, 1, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 3, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 3, ConveyorBeltBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 6, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 3, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 3, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 3, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 3);

        // Row 4
        assertBlockBehaviors(board, 0, 4, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 1, 4, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 4, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 4, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 4, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 4, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 4, ExpressConveyorBeltBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 7, 4, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 4, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 4, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 4, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 4, PusherBehaviour.class, WallBehaviour.class);

        // Row 5
        assertBlockBehaviors(board, 0, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 5, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 5, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 5, PitBehaviour.class);
        assertBlockBehaviors(board, 6, 5, PitBehaviour.class);
        assertBlockBehaviors(board, 7, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 5, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 5, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 5, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 5, ExpressConveyorBeltBehaviour.class);

        // Row 6
        assertBlockBehaviors(board, 0, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 6, ConveyorBeltBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 4, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 6, PitBehaviour.class);
        assertBlockBehaviors(board, 6, 6, PitBehaviour.class);
        assertBlockBehaviors(board, 7, 6, ConveyorBeltBehaviour.class, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 8, 6, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 6, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 6, ExpressConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 6);

        // Row 7
        assertBlockBehaviors(board, 0, 7, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 1, 7, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 7, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 7, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 7, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 7, ConveyorBeltBehaviour.class, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 6, 7, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 7, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 7, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 7, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 7, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 7, PusherBehaviour.class, WallBehaviour.class);

        // Row 8
        assertBlockBehaviors(board, 0, 8);
        assertBlockBehaviors(board, 1, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 8, ExpressConveyorBeltBehaviour.class, WallBehaviour.class, LaserBehaviour.class);
        assertBlockBehaviors(board, 7, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 8, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 8, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 8);

        // Row 9
        assertBlockBehaviors(board, 0, 9, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 1, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 9, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 9, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 9, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 11, 9, PusherBehaviour.class, WallBehaviour.class);

        // Row 10
        assertBlockBehaviors(board, 0, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 1, 10, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 2, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 3, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 4, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 5, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 8, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 9, 10, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 10, 10, ExpressConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 10);

        // Row 11
        assertBlockBehaviors(board, 0, 11, RepairBehaviour.class);
        assertNoBehaviors(board, 1, 11);
        assertBlockBehaviors(board, 2, 11, PusherBehaviour.class, WallBehaviour.class);
        assertNoBehaviors(board, 3, 11);
        assertBlockBehaviors(board, 4, 11, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 5, 11, ConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 6, 11, ExpressConveyorBeltBehaviour.class);
        assertBlockBehaviors(board, 7, 11, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 8, 11, RepairBehaviour.class);
        assertBlockBehaviors(board, 9, 11, PusherBehaviour.class, WallBehaviour.class);
        assertBlockBehaviors(board, 10, 11, ExpressConveyorBeltBehaviour.class);
        assertNoBehaviors(board, 11, 11);
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
