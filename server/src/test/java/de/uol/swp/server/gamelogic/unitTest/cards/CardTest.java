package de.uol.swp.server.gamelogic.unitTest.cards;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.cards.Direction;
import de.uol.swp.server.gamelogic.cards.Straight;
import de.uol.swp.server.gamelogic.cards.Turn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CardTest {

    private final Position position = new Position(0, 0);
    private final CardinalDirection cardinalDirection = CardinalDirection.North;

    @Test
    void testCardConstructorWithBehaviour() {
        Card card = new Card(1, new Turn(Direction.Left), 3);
        Assertions.assertEquals(1, card.getId());
        Assertions.assertEquals(3, card.getPriority());
        Assertions.assertEquals(Direction.Left, card.getDirectionCard());
        Assertions.assertEquals(0, card.getMoves());
        Assertions.assertFalse(card.getUTurn());
    }

    @Test
    void testCardConstructorWithPowerDown() {
        Card card = new Card(1, new Turn(Direction.Left), 3);
        Assertions.assertEquals(1, card.getId());
        Assertions.assertEquals(3, card.getPriority());
        Assertions.assertNotNull(card.getDirectionCard());
        Assertions.assertEquals(0, card.getMoves());
        Assertions.assertFalse(card.getUTurn());
    }

    @Test
    void testCardConstructorWithBehaviourTypeValid() throws Exception {
        Card card = new Card(3, "4", 2);
        Assertions.assertEquals(3, card.getId());
        Assertions.assertEquals(2, card.getPriority());
        Assertions.assertNull(card.getDirectionCard());
        Assertions.assertEquals(0, card.getMoves());
        Assertions.assertTrue(card.getUTurn());
    }

    @Test
    void testCardConstructorWithBehaviourTypeInvalid() {
        Assertions.assertThrows(Exception.class, () -> {
            Card card = new Card(4, "2", 1);
        });
    }

    @Test
    void testExecuteBehaviour() {
        Card card = new Card(5, new Straight(2), 3);
        Robot robot = new Robot(123,position, cardinalDirection);
        card.executeBehaviour(robot);
        Assertions.assertEquals(robot.getPosition(), robot.getPosition());
    }
}
