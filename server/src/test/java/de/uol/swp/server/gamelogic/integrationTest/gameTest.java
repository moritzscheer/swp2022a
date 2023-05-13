package de.uol.swp.server.gamelogic.integrationTest;

import de.uol.swp.server.gamelogic.AbstractPlayer;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.common.game.Position;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;
import de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour;
import de.uol.swp.common.game.enums.CardinalDirection;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class gameTest {
    // Create Players

    private static final AbstractPlayer[] players = new AbstractPlayer[4];

    // Create robots for players
    private static final Robot[] robots = new Robot[4];

    // Create Cards
    private static final Card[] allCards = new Card[84];

    private static final Position pos1 = new Position(1, 1);

    private static final AbstractTileBehaviour[] behaviours1 = new AbstractTileBehaviour[1];

    // Create board
    private static final Block[][] board = new Block[16][12];
    private int checkPointNumber = 1;
    private static final int checkPointCount = 4;

    @Before
    public void setup() throws Exception {
        robots[0] = new Robot("", pos1, true, CardinalDirection.East);

        // Create behaviour of the board
        // check points
        Position[] positions = {new Position(2, 8), new Position(8, 10), new Position(5, 2)};

        for (Position p : positions) {
            board[p.x - 1][p.y - 1] =
                    new Block(
                            new CheckPointBehaviour(List.of(robots), board, p, checkPointNumber), "CP1", p);
            checkPointNumber += 1;
        }

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 12; j++) {
                if (board[i][j] != null) System.out.print(((Block) board[i][j]).getImgPath());
                else System.out.print(" . ");
            }
            System.out.println("");
        }
        behaviours1[0] = new CheckPointBehaviour(List.of(robots), board, pos1, checkPointNumber);
        board[0][0] = new Block(behaviours1, "", pos1);
    }

    /**
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-04-25
     */
    @Test
    public void writeTest() {
        // TODO

    }
}
