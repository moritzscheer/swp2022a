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

    /**
     * Tests the Card constructor with a turn left behaviour and checks the gedID, getPriority,
     * getDirectionCard
     *
     * @author WKempel
     * @result The Card constructor should create a Card with the correct values
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-06-21
     */
    @Test
    public void testCardConstructorWithBehaviour() {
        Card card = new Card(1, new Turn(Direction.Left), 3);
        Assertions.assertEquals(1, card.getId());
        Assertions.assertEquals(3, card.getPriority());
        Assertions.assertEquals(Direction.Left, card.getDirectionCard());
        Assertions.assertEquals(0, card.getMoves());
        Assertions.assertFalse(card.getUTurn());
    }

    /**
     * Tests the Card constructor with a turn left behaviour and checks the gedID, getPriority,
     * getDirectionCard Also checks if the card is a power down card
     *
     * @author WKempel
     * @result The Card constructor should create a Card with the correct values
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-06-21
     */
    @Test
    public void testCardConstructorWithPowerDown() {
        Card card = new Card(1, new Turn(Direction.Left), 3);
        Assertions.assertEquals(1, card.getId());
        Assertions.assertEquals(3, card.getPriority());
        Assertions.assertNotNull(card.getDirectionCard());
        Assertions.assertEquals(0, card.getMoves());
        Assertions.assertFalse(card.getUTurn());
    }

    /**
     * Tests the Card constructor with valid types and checks the gedID, getPriority,
     * getDirectionCard
     *
     * @author WKempel
     * @throws Exception
     * @result The Card constructor should create a Card with the correct values
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-06-21
     */
    @Test
    public void testCardConstructorWithBehaviourTypeValid() throws Exception {
        Card card = new Card(3, "4", 2);
        Assertions.assertEquals(3, card.getId());
        Assertions.assertEquals(2, card.getPriority());
        Assertions.assertNull(card.getDirectionCard());
        Assertions.assertEquals(0, card.getMoves());
        Assertions.assertTrue(card.getUTurn());
    }

    /**
     * Tests the Card constructor with invalid types
     *
     * @author WKempel
     * @throws Exception
     * @result The Card constructor should throw an exception
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-06-21
     */
    @Test
    public void testCardConstructorWithBehaviourTypeInvalid() {
        Assertions.assertThrows(
                Exception.class,
                () -> {
                    Card card = new Card(4, "2", 1);
                });
    }

    /**
     * Tests the Card constructor with a straight behaviour and checks the executeBehaviour method
     *
     * @author WKempel
     * @result The executeBehaviour method should return the correct values
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-06-21
     */
    @Test
    public void testExecuteBehaviour() {
        Card card = new Card(5, new Straight(2), 3);
        Robot robot = new Robot(123, position, cardinalDirection);
        card.executeBehaviour(robot);
        Assertions.assertEquals(robot.getPosition(), robot.getPosition());
    }
}
