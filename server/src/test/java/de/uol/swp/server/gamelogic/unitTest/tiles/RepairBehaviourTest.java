package de.uol.swp.server.gamelogic.unitTest.tiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;
import de.uol.swp.server.gamelogic.tiles.RepairBehaviour;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Test RepairBehaviour
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-02-26
 */
public class RepairBehaviourTest {

    private static final Position pos1 = new Position(1, 1);
    private static final Position pos2 = new Position(1, 2);
    private static final Robot[] robots = new Robot[2];
    private static final AbstractTileBehaviour[] behaviours1 = new AbstractTileBehaviour[1];
    private static final AbstractTileBehaviour[] behaviours2 = new AbstractTileBehaviour[1];
    private static final Block[][] board = new Block[1][2];
    private static final int repairSiteKey1 = 1; // (1)single-wrench
    private static final int repairSiteKey2 = 2; // (2)crossed wrench/hammer

    @Before
    public void setup() throws Exception {
        robots[0] = new Robot(1, pos1, CardinalDirection.East);
        robots[1] = new Robot(2, pos2, CardinalDirection.East);
        behaviours1[0] = new RepairBehaviour(List.of(robots), board, pos1, repairSiteKey1);
        behaviours2[0] = new RepairBehaviour(List.of(robots), board, pos2, repairSiteKey2);
        board[0][0] = new Block(behaviours1, "", pos1);
        board[0][1] = new Block(behaviours2, "", pos2);
    }

    /**
     * Test when robot is on single-wrench repair station
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see RepairBehaviour
     * @see Robot;
     * @since 2023-02-26
     */
    @Test
    public void robotOnSingleWrenchTest() {
        // robot receives 2 damage tokens for reentering the race
        int previousDamage = robots[0].getDamageToken();
        robots[0].setDamageByReenteringRace();
        assertEquals(previousDamage + 2, robots[0].getDamageToken());
        previousDamage = robots[0].getDamageToken();

        // robot is in repair station
        behaviours1[0].onCardEnding(4);

        assertEquals(previousDamage - 1, robots[0].getDamageToken());
        assertEquals(
                ((RepairBehaviour) behaviours1[0]).getCheckPointPosition(),
                robots[0].getLastBackupCopyPosition());
    }

    /**
     * Test when robot is on crossed wrench/hammer repair station
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.RepairBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-02-26
     */
    @Test
    public void robotOnCrossedWrenchHammerTest() {
        // robot receives 2 damage tokens for reentering the race
        int previousDamage = robots[1].getDamageToken();
        robots[1].setDamageByReenteringRace();
        assertEquals(previousDamage + 2, robots[1].getDamageToken());
        previousDamage = robots[1].getDamageToken();

        // robot is in repair station
        int previousOptionCard = robots[1].getOptionCard();
        behaviours2[0].onCardEnding(4);

        assertEquals(previousDamage - 2, robots[1].getDamageToken());
        assertEquals(
                ((RepairBehaviour) behaviours2[0]).getCheckPointPosition(),
                robots[1].getLastBackupCopyPosition());
    }

    /**
     * Test when robot is on crossed wrench/hammer repair station
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.tiles.RepairBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-06-21
     */
    @Test
    public void fixDamageTokenTest() {
        RepairBehaviour repairBehaviour = (RepairBehaviour) behaviours1[0];
        repairBehaviour.fixDamageToken(0);
        assertEquals(0, robots[0].getDamageToken());
    }

    /**
     * Test the setBackupCopy method
     *
     * @author WKempel
     * @see de.uol.swp.server.gamelogic.tiles.RepairBehaviour
     * @see de.uol.swp.server.gamelogic.Robot;
     * @since 2023-06-21
     */
    @Test
    public void setBackupCopyTest() {
        RepairBehaviour repairBehaviour = (RepairBehaviour) behaviours1[0];
        repairBehaviour.setBackupCopy(1);

        assertEquals(pos1, robots[0].getLastBackupCopyPosition());
    }

    /**
     * Tests the getImage method
     *
     * @author WKempel
     * @result The image is returned correctly
     * @since 2023-06-21
     */
    @Test
    public void testGetImage() {
        List<int[]> image = new ArrayList<>();
        image = behaviours1[0].getImage();
        assertEquals(1, image.size());
        assertEquals(25, image.get(0)[0]);
    }
}
