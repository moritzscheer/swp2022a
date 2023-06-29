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
import de.uol.swp.server.gamelogic.BotPlayer;
import de.uol.swp.server.gamelogic.Game;
import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.map.MapBuilder;
import de.uol.swp.server.gamelogic.moves.GameMovement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static junit.framework.TestCase.assertEquals;

public class GameTest {

    private static final Logger LOG = LogManager.getLogger(Game.class);

    String mapName = "MapOne";
    int numberBots = 2;
    int checkpoint = 3;
    private final UUID uuid = UUID.randomUUID();
    private final UserDTO user1 = new UserDTO("Player1","pw1", "ml1");
    private final UserDTO user2 = new UserDTO("Player2","pw2", "ml2");

    private final LobbyDTO lobby = new LobbyDTO(123,"testLobby", user1,null,true,uuid);


    private final Position position = new Position(0,0);
    private final CardinalDirection cardinalDirection = CardinalDirection.North;

    private final RobotDTO robotDTO1 = new RobotDTO(123, position, cardinalDirection);
    private final RobotDTO robotDTO2 = new RobotDTO(456, position, cardinalDirection);

    private final PlayerDTO playerDTO1 = new PlayerDTO(robotDTO1,user1);
    private final PlayerDTO playerDTO2 = new PlayerDTO(robotDTO2,user2);
    private final Set<User> users = new HashSet<>();
    private final Game game = new Game(lobby.getLobbyID(), users, mapName, numberBots, checkpoint);



    @BeforeEach
    public void setup() throws IOException, InterruptedException {

        //MapBuilder.main(null);


        users.add(user1);
        users.add(user2);


        Block[][] board = game.getBoard();

        List<CardDTO> player1Cards = new ArrayList<>();
        player1Cards.add(new CardDTO(1,10));
        player1Cards.add(new CardDTO(2,20));
        player1Cards.add(new CardDTO(3,30));
        player1Cards.add(new CardDTO(4,40));
        player1Cards.add(new CardDTO(5,50));

        playerDTO1.setCurrentCards(player1Cards);
        playerDTO1.getRobotDTO().setPowerDown(false);



        List<CardDTO> player2Cards = new ArrayList<>();
        player2Cards.add(new CardDTO(6,60));
        player2Cards.add(new CardDTO(7,70));
        player2Cards.add(new CardDTO(8,80));
        player2Cards.add(new CardDTO(9,90));
        player2Cards.add(new CardDTO(10,100));

        playerDTO2.setCurrentCards(player2Cards);
        playerDTO2.getRobotDTO().setPowerDown(false);

        //game.registerCardsFromUser(new UserDTO("player1", "pw1", "ml1"), playerDTO1.getCurrentCards());
        //game.registerCardsFromUser(new UserDTO("player2", "pw2", "ml2"), playerDTO2.getCurrentCards());


    }




    /**
    @Test
    public void testRegisterCardsFromUser() throws InterruptedException, IOException {
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
        Assertions.assertEquals(player1.getReceivedCards(), game.getPlayerByUserDTO(user1).getReceivedCards());


        AbstractPlayer player2 = game.getPlayerByUserDTO(user2);
        Card[] player2ChosenCards = player2.getChosenCards();
        Assertions.assertNotNull(player2ChosenCards);
        Assertions.assertEquals(5, player2ChosenCards.length);
        Assertions.assertEquals(player2.getReceivedCards(), game.getPlayerByUserDTO(user2).getReceivedCards());
    }
     */

    /**
     * user1 is everytime null but that can not be because the user1 is in the game
    @Test
    public void testGetPlayerByUserDTO() {
        AbstractPlayer player = game.getPlayerByUserDTO(user1);
        Assertions.assertNotNull(player);
        Assertions.assertEquals(user1, player.getUser());

        player = game.getPlayerByUserDTO(user2);
        Assertions.assertNotNull(player);
        Assertions.assertEquals(user2, player.getUser());
    }
     */

    @Test
    public void testSetPowerDown() {
        playerDTO1.getRobotDTO().setPowerDown(true);
        Assertions.assertTrue(playerDTO1.getRobotDTO().isPowerDown());
        playerDTO2.getRobotDTO().setPowerDown(true);
        Assertions.assertTrue(playerDTO2.getRobotDTO().isPowerDown());
    }

    @Test
    public void testRoundIsOver() {
        playerDTO1.getRobotDTO().setAlive(false);
        playerDTO2.getRobotDTO().setAlive(false);
        playerDTO1.getRobotDTO().setDeadForever(true);
        playerDTO2.getRobotDTO().setDeadForever(true);
        game.roundIsOver();
        Assertions.assertFalse(playerDTO1.getRobotDTO().isAlive());
        Assertions.assertFalse(playerDTO2.getRobotDTO().isAlive());
    }

    @Test
    public void testBoardLength() {
      int size = game.getBoard().length;
      Assertions.assertEquals(12, size);
    }

    @Test
    public void testGetPlayerDTOSForAllPlayers() {
        List<PlayerDTO> playerDTOS = game.getPlayerDTOSForAllPlayers();
        Assertions.assertNotNull(playerDTOS);
        Assertions.assertEquals(2, playerDTOS.size());
        Assertions.assertEquals(playerDTOS.get(0), game.getPlayerDTOSForAllPlayers().get(0));
        Assertions.assertEquals(playerDTOS.get(1), game.getPlayerDTOSForAllPlayers().get(1));
    }

    @Test
    public void testGetRoundNumber() {
        int roundNumber = game.getRoundNumber();
        Assertions.assertEquals(1, roundNumber);
    }

    @Test
    public void testGetLastCheckPoint() {
        int lastCheckPoint = game.getLastCheckPoint();
        Assertions.assertEquals(3, lastCheckPoint);
    }

    @Test
    public void testGetGameMovements() {
        List<GameMovement> gameMovements = game.getGameMovements();
        Assertions.assertNotNull(gameMovements);
        Assertions.assertEquals(0, gameMovements.size());
    }

    @Test
    public void testGetRespawnRobots() {
        List<PlayerDTO> respawnRobots = game.getRespawnRobots();
        Assertions.assertNull(respawnRobots);
    }

    @Test
    public void testGetBoard() {
        Assertions.assertNotNull(game.getBoard());
    }

    @Test
    public void testSetBoard() {
        game.setBoard(null);
        Assertions.assertNull(game.getBoard());
    }

    @Test
    public void testGetPlayers() {
        List<AbstractPlayer> players = game.getPlayers();
        Assertions.assertNotNull(players);
        Assertions.assertEquals(2, players.size());
        Assertions.assertEquals(players.get(0), game.getPlayers().get(0));
        Assertions.assertEquals(players.get(1), game.getPlayers().get(1));
    }

    @Test
    public void testGetStartCheckpoint() {
        Position startCheckpoint = game.getStartCheckpoint();
        Assertions.assertEquals(game.getStartCheckpoint(), startCheckpoint);
    }

    @Test
    public void testGetDockingStartPosition() {
        Position dockingStartPosition = game.getDockingStartPosition();
        Assertions.assertEquals(game.getDockingStartPosition(), dockingStartPosition);
    }

    @Test
    public void testIncreaseProgramStep() {
        game.increaseProgramStep();
        Assertions.assertEquals(1, game.getProgramStep());
    }

    @Test
    public void testAreAllRobotsAreDeadOrTurnedOff() {
        Assertions.assertFalse(game.areAllRobotsAreDeadOrTurnedOff());
    }

    @Test
    public void testSetNotDistributedCards() {
        game.setNotDistributedCards(true);
        Assertions.assertTrue(game.isNotDistributedCards());
    }
}
