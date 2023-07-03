package de.uol.swp.server.gamelogic.unitTest;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.dto.RobotDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.AbstractPlayer;
import de.uol.swp.server.gamelogic.Block;
import de.uol.swp.server.gamelogic.Game;
import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.moves.GameMovement;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameTest {

    String mapName = "MapOne";
    int numberBots = 2;
    int checkpoint = 3;
    private final UUID uuid = UUID.randomUUID();
    private final UserDTO user1 = new UserDTO("Player1", "pw1", "ml1");
    private final UserDTO user2 = new UserDTO("Player2", "pw2", "ml2");
    private Game game;
    private final LobbyDTO lobby = new LobbyDTO(123, "testLobby", user1, null, true, uuid);

    private final Position position = new Position(0, 0);
    private final CardinalDirection cardinalDirection = CardinalDirection.North;

    private final RobotDTO robotDTO1 = new RobotDTO(123, position, cardinalDirection);
    private final RobotDTO robotDTO2 = new RobotDTO(456, position, cardinalDirection);

    private final PlayerDTO playerDTO1 = new PlayerDTO(robotDTO1, user1);
    private final PlayerDTO playerDTO2 = new PlayerDTO(robotDTO2, user2);

    /**
     * Setup for the tests to create a game
     *
     * @author WKempel
     * @throws IOException
     * @throws InterruptedException
     * @see de.uol.swp.server.gamelogic.Game
     * @since 2023-06-28
     */
    @BeforeEach
    public void setup() throws IOException, InterruptedException {

        // MapBuilder.main(null);
        try {
            Set<User> users = new HashSet<>();
            users.add(user1);
            users.add(user2);

            game = new Game(lobby.getLobbyID(), users, mapName, numberBots, checkpoint, -1);
            Block[][] board = game.getBoard();

            List<CardDTO> player1Cards = new ArrayList<>();
            player1Cards.add(new CardDTO(1, 10));
            player1Cards.add(new CardDTO(2, 20));
            player1Cards.add(new CardDTO(3, 30));
            player1Cards.add(new CardDTO(4, 40));
            player1Cards.add(new CardDTO(5, 50));

            playerDTO1.setCurrentCards(player1Cards);
            playerDTO1.getRobotDTO().setPowerDown(false);

            List<CardDTO> player2Cards = new ArrayList<>();
            player2Cards.add(new CardDTO(6, 60));
            player2Cards.add(new CardDTO(7, 70));
            player2Cards.add(new CardDTO(8, 80));
            player2Cards.add(new CardDTO(9, 90));
            player2Cards.add(new CardDTO(10, 100));

            playerDTO2.setCurrentCards(player2Cards);
            playerDTO2.getRobotDTO().setPowerDown(false);

            game.registerCardsFromUser(user1, playerDTO1.getCurrentCards());
            game.registerCardsFromUser(user2, playerDTO2.getCurrentCards());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests if the game is created correctly and the registerCardsFromUser method
     *
     * @author WKempel
     * @throws InterruptedException
     * @result The game is created correctly and the registerCardsFromUser method works
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-06-28
     */
    @Test
    public void testRegisterCardsFromUser() throws InterruptedException {
        try {
            System.out.println((new File(".").getAbsolutePath()));
            game.distributeProgramCards();

            boolean result = game.registerCardsFromUser(user1, playerDTO1.getCurrentCards());
            Assertions.assertFalse(result);

            result = game.registerCardsFromUser(user2, playerDTO2.getCurrentCards());
            Assertions.assertTrue(result);

            AbstractPlayer player1 = game.getPlayerByUserDTO(user1);
            Card[] player1ChosenCards = player1.getChosenCards();
            Assertions.assertNotNull(player1ChosenCards);
            Assertions.assertEquals(5, player1ChosenCards.length);
            Assertions.assertEquals(
                    player1.getReceivedCards(), game.getPlayerByUserDTO(user1).getReceivedCards());

            AbstractPlayer player2 = game.getPlayerByUserDTO(user2);
            Card[] player2ChosenCards = player2.getChosenCards();
            Assertions.assertNotNull(player2ChosenCards);
            Assertions.assertEquals(5, player2ChosenCards.length);
            Assertions.assertEquals(
                    player2.getReceivedCards(), game.getPlayerByUserDTO(user2).getReceivedCards());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests if the game is created correctly and the getPlayerByUserDTO method
     *
     * @author WKempel
     * @result The game is created correctly and the getPlayerByUserDTO method works, all players
     *     found and exist in the game
     * @see de.uol.swp.server.gamelogic
     * @since 2023-06-28
     */
    @Test
    public void testGetPlayerByUserDTO() {
        try {
            AbstractPlayer player = game.getPlayerByUserDTO(user1);
            Assertions.assertNotNull(player);
            Assertions.assertEquals(user1, player.getUser());

            player = game.getPlayerByUserDTO(user2);
            Assertions.assertNotNull(player);
            Assertions.assertEquals(user2, player.getUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the setPowerDown method
     *
     * @author WKempel
     * @result The setPowerDown method works because the powerDown status changed
     * @since 2023-06-28
     */
    @Test
    public void testSetPowerDown() {
        try {
            playerDTO1.getRobotDTO().setPowerDown(true);
            Assertions.assertTrue(playerDTO1.getRobotDTO().isPowerDown());
            playerDTO2.getRobotDTO().setPowerDown(true);
            Assertions.assertTrue(playerDTO2.getRobotDTO().isPowerDown());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the roundIsOver method
     *
     * @author WKempel
     * @result The roundIsOver method works because the players are dead
     * @since 2023-06-28
     */
    @Test
    public void testRoundIsOver() {
        try {
            playerDTO1.getRobotDTO().setAlive(false);
            playerDTO2.getRobotDTO().setAlive(false);
            playerDTO1.getRobotDTO().setDeadForever(true);
            playerDTO2.getRobotDTO().setDeadForever(true);
            game.roundIsOver();
            Assertions.assertFalse(playerDTO1.getRobotDTO().isAlive());
            Assertions.assertFalse(playerDTO2.getRobotDTO().isAlive());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the getBoard method
     *
     * @author WKempel
     * @result The getBoard method works because the board is not null and has the correct size
     * @since 2023-06-28
     */
    @Test
    public void testBoardLength() {
        try {
            int size = game.getBoard().length;
            Assertions.assertEquals(12, size);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the getPlayerDTOSForAllPlayers method
     *
     * @author WKempel
     * @result The getPlayerDTOSForAllPlayers method works because the playerDTOs are not null, have
     *     the correct size and contains the correct players
     * @since 2023-06-28
     */
    @Test
    public void testGetPlayerDTOSForAllPlayers() {
        try {
            List<PlayerDTO> playerDTOS = game.getPlayerDTOSForAllPlayers();
            Assertions.assertNotNull(playerDTOS);
            Assertions.assertEquals(4, playerDTOS.size());
            Assertions.assertEquals(playerDTOS.get(0), game.getPlayerDTOSForAllPlayers().get(0));
            Assertions.assertEquals(playerDTOS.get(1), game.getPlayerDTOSForAllPlayers().get(1));
            Assertions.assertEquals(playerDTOS.get(2), game.getPlayerDTOSForAllPlayers().get(2));
            Assertions.assertEquals(playerDTOS.get(3), game.getPlayerDTOSForAllPlayers().get(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the getRoundNumber method
     *
     * @author WKempel
     * @result The getRoundNumber method works because the roundNumber is correct
     * @since 2023-06-28
     */
    @Test
    public void testGetRoundNumber() {
        try {
            int roundNumber = game.getRoundNumber();
            Assertions.assertEquals(1, roundNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the getLastCheckPoint method
     *
     * @author WKempel
     * @result The getLastCheckPoint method works because the lastCheckPoint is correct
     * @since 2023-06-28
     */
    @Test
    public void testGetLastCheckPoint() {
        try {
            int lastCheckPoint = game.getLastCheckPoint();
            Assertions.assertEquals(3, lastCheckPoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the getGameMovements method
     *
     * @author WKempel
     * @result The getGameMovements method works because the gameMovements are not null and have the
     *     correct size
     * @since 2023-06-28
     */
    @Test
    public void testGetGameMovements() {
        try {
            List<GameMovement> gameMovements = game.getGameMovements();
            Assertions.assertNotNull(gameMovements);
            Assertions.assertEquals(0, gameMovements.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the getRespawnRobots method
     *
     * @author WKempel
     * @result The getRespawnRobots method works because no robot died and has to respawn
     * @since 2023-06-28
     */
    @Test
    public void testGetRespawnRobots() {
        try {
            List<PlayerDTO> respawnRobots = game.getRespawnRobots();
            Assertions.assertNull(respawnRobots);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the getBoard method
     *
     * @author WKempel
     * @result The getBoard method works because the board is not null
     * @since 2023-06-28
     */
    @Test
    public void testGetBoard() {
        try {
            Assertions.assertNotNull(game.getBoard());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the setBoard method
     *
     * @author WKempel
     * @result The setBoard method works because the board is null
     * @since 2023-06-28
     */
    @Test
    public void testSetBoard() {
        try {
            game.setBoard(null);
            Assertions.assertNull(game.getBoard());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the getPlayers method
     *
     * @author WKempel
     * @result The getPlayers method works because the players are not null, have the correct size
     *     and contains the correct players
     * @since 2023-06-28
     */
    @Test
    public void testGetPlayers() {
        try {
            List<AbstractPlayer> players = game.getPlayers();
            Assertions.assertNotNull(players);
            Assertions.assertEquals(4, players.size());
            Assertions.assertEquals(players.get(0), game.getPlayers().get(0));
            Assertions.assertEquals(players.get(1), game.getPlayers().get(1));
            Assertions.assertEquals(players.get(2), game.getPlayers().get(2));
            Assertions.assertEquals(players.get(3), game.getPlayers().get(3));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the getStartCheckpoint method
     *
     * @author WKempel
     * @result The getStartCheckpoint method works because the startCheckpoint is not null every
     *     robot has the same startCheckpoint
     * @since 2023-06-28
     */
    @Test
    public void testGetStartCheckpoint() {
        try {
            Position startCheckpoint = game.getStartCheckpoint();
            Assertions.assertEquals(game.getStartCheckpoint(), startCheckpoint);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the getDockingStartPosition method
     *
     * @author WKempel
     * @result The getDockingStartPosition method works because the dockingStartPosition is not null
     * @since 2023-06-28
     */
    @Test
    public void testGetDockingStartPosition() {
        try {
            Position dockingStartPosition = game.getDockingStartPosition();
            Assertions.assertEquals(game.getDockingStartPosition(), dockingStartPosition);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the increaseProgramStep method
     *
     * @author WKempel
     * @result The increaseProgramStep method works because the programStep is correct
     * @since 2023-06-28
     */
    @Test
    public void testIncreaseProgramStep() {
        try {
            game.increaseProgramStep();
            Assertions.assertEquals(1, game.getProgramStep());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the areAllRobotsAreDead method
     *
     * @author WKempel
     * @result The areAllRobotsAreDead method returns true when all robots are dead, so it is not
     *     necessary to keep calculating next program steps
     * @since 2023-06-28
     */
    @Test
    public void testAreAllRobotsAreDead() {
        try {
            Assertions.assertFalse(game.areAllRobotsAreDead());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests the setNotDistributedCards method
     *
     * @author WKempel
     * @result The setNotDistributedCards method works because the notDistributedCards is true after
     *     setting it
     * @since 2023-06-28
     */
    @Test
    public void testSetNotDistributedCards() {
        try {
            game.setNotDistributedCards(true);
            Assertions.assertTrue(game.isNotDistributedCards());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
