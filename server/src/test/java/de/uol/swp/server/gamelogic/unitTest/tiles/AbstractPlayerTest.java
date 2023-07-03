package de.uol.swp.server.gamelogic.unitTest.tiles;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.Player;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.cards.Card;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class AbstractPlayerTest {

    private final Position position = new Position(1, 1);
    private final CardinalDirection cardinalDirection = CardinalDirection.North;
    private final Robot robot = new Robot(123, position, cardinalDirection);
    private final Card[] cards = new Card[9];
    private final Card[] cards2 =
            new Card[] {
                new Card(0), new Card(1), new Card(2),
            };
    private final Card[] chosenCards = new Card[5];
    private final UserDTO userDTO = new UserDTO("testUser", "testPassword", "testMail");
    private final Player player = new Player(userDTO, position, robot.getID());

    /**
     * Sets up the test environment
     *
     * @author WKempel
     * @since 2023-06-23
     */
    @BeforeEach
    public void setUp() {
        int cadID = 0;
        for (int i = 0; i < 9; i++) {
            cards[i] = new Card(cadID + i);
            chosenCards[i] = new Card(cadID + i);
        }
    }

    /**
     * Tests the receiveCards method
     *
     * @author WKempel
     * @result The receiveCards method should return true if the cards are received and the length
     *     of the cards-array musst be equal
     * @see de.uol.swp.server.gamelogic.tiles
     * @since 2023-06-23
     */
    @Test
    public void testReceiveCards() {
        player.setReceivedCards(cards);
        Assertions.assertEquals(cards.length, player.getReceivedCards().length);
    }

    /**
     * Tests the chooseCardsOrder method
     *
     * @author WKempel
     * @result The chooseCardsOrder method should return true if the cards are chosen and the length
     *     of the chosenCards-array musst be equal
     * @see de.uol.swp.server.gamelogic.tiles
     * @since 2023-06-23
     */
    @Test
    public void testChooseCardsOrder() {
        player.chooseCardsOrder(chosenCards);
        Assertions.assertEquals(chosenCards.length, player.getChosenCards().length);
    }

    /**
     * Tests the getReceivedCards getter
     *
     * @author WKempel
     * @result The getReceivedCards method should return the correct value
     * @see de.uol.swp.server.gamelogic.tiles
     * @since 2023-06-23
     */
    @Test
    public void testGetReceivedCards() {
        player.setReceivedCards(cards);
        Assertions.assertEquals(cards.length, player.getReceivedCards().length);
    }

    /**
     * Tests the getChosenCards getter
     *
     * @author WKempel
     * @result The getChosenCards method should return the correct value
     * @see de.uol.swp.server.gamelogic.tiles
     * @since 2023-06-23
     */
    @Test
    public void testGetChosenCards() {
        player.chooseCardsOrder(chosenCards);
        Assertions.assertEquals(chosenCards.length, player.getChosenCards().length);
    }

    /**
     * Tests the playerCard method
     *
     * @author WKempel
     * @result The playerCard method should return the correct value after setting it
     * @see de.uol.swp.server.gamelogic.tiles
     * @since 2023-06-23
     */
    @Test
    public void testPlayerCard() {
        Assertions.assertEquals(0, player.getNumCardToPlay());
        player.setNumCardToPlay(1);
        Assertions.assertEquals(1, player.getNumCardToPlay());
    }

    /**
     * Tests the setUser method
     *
     * @author WKempel
     * @result The setUser method should return the correct value after setting it
     * @see de.uol.swp.server.gamelogic.tiles
     * @since 2023-06-23
     */
    @Test
    public void testSetUser() {
        Assertions.assertEquals(userDTO, player.getUser());
        player.setUser(new UserDTO("testUser2", "testPassword2", "testMail2"));
        Assertions.assertEquals("testUser2", player.getUser().getUsername());
    }

    /**
     * Tests the setReceivedCards method
     *
     * @author WKempel
     * @result The setReceivedCards method should return the correct value and length after setting
     *     it
     * @see de.uol.swp.server.gamelogic.tiles
     * @since 2023-06-23
     */
    @Test
    public void testSetReceivedCards() {
        player.setReceivedCards(cards2);
        Assertions.assertEquals(cards2.length, player.getReceivedCards().length);
    }

    /**
     * Tests the getNumCardToPlay method
     *
     * @author WKempel
     * @result The getNumCardToPlay method should return the correct value after using the setter
     * @see de.uol.swp.server.gamelogic.tiles
     * @since 2023-06-23
     */
    @Test
    public void testGetNumCardToPlay() {
        player.setNumCardToPlay(5);
        Assertions.assertEquals(5, player.getNumCardToPlay());
    }

    /**
     * Tests the setNumCardToPlay method
     *
     * @author WKempel
     * @result The setNumCardToPlay method should return the correct value after setting it
     * @see de.uol.swp.server.gamelogic.tiles
     * @since 2023-06-23
     */
    @Test
    public void testSetNumCardToPlay() {
        player.setNumCardToPlay(5);
        Assertions.assertEquals(5, player.getNumCardToPlay());
        player.setNumCardToPlay(8);
        Assertions.assertEquals(8, player.getNumCardToPlay());
    }
}
