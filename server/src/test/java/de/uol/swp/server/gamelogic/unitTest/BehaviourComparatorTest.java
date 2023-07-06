package de.uol.swp.server.gamelogic.unitTest;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.BehaviourTypeComparator;
import de.uol.swp.server.gamelogic.tiles.AbstractTileBehaviour;
import de.uol.swp.server.gamelogic.tiles.ConveyorBeltBehaviour;
import de.uol.swp.server.gamelogic.tiles.LaserBehaviour;
import de.uol.swp.server.gamelogic.tiles.enums.ArrowType;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * Test BehaviourComparatorTest
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-07-07
 */
public class BehaviourComparatorTest {

    /**
     * Test correct Order
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-07-07
     */
    @Test
    public void testCorrectOrder() {
        AbstractTileBehaviour[] behaviourList = new AbstractTileBehaviour[2];

        behaviourList[0] =
                new LaserBehaviour(
                        null,
                        null,
                        new int[] {1, 2, 3, 4, 5},
                        new Position(0, 0),
                        CardinalDirection.East,
                        1,
                        true,
                        true);

        behaviourList[1] =
                new ConveyorBeltBehaviour(
                        null,
                        null,
                        new Position(0, 0),
                        ArrowType.Straight,
                        CardinalDirection.South);

        Arrays.sort(behaviourList, new BehaviourTypeComparator());

        Assertions.assertTrue(behaviourList[0] instanceof ConveyorBeltBehaviour);
        Assertions.assertTrue(behaviourList[1] instanceof LaserBehaviour);
    }
}
