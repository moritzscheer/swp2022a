package de.uol.swp.server.gamelogic.unitTest;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.message.PlayerIsReadyMessage;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.AbstractPlayer;
import de.uol.swp.server.gamelogic.GameManagement;
import de.uol.swp.server.gamelogic.Game;
import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.*;

public class GameManagementTest {
    private GameManagement gameManagement;
    private static final int lobbyID = 1;
    private static final String mapName = "MapOne";
    private static final int numberBots = 2;
    private static final int checkPoints = 5;
    private Set<User> users;

    private GameDTO gameDTO;
    private UserDTO user;

    @Before
    public void setUp() {
        gameManagement = new GameManagement();
        users = new HashSet<>();
        user = new UserDTO("test1", "pass", "email");
        users.add(user);

        // Create a game
        gameDTO = gameManagement.createNewGame(lobbyID, mapName, numberBots, checkPoints, users);
    }

    @Test
    public void testCreateNewGame() {
        assertNotNull(gameDTO);
        assertEquals(users.size() + numberBots, gameManagement.getGame(lobbyID).get().getPlayers().size());
        assertEquals(checkPoints, gameManagement.getGame(lobbyID).get().getLastCheckPoint());
        assertEquals(gameDTO.getPlayers().size(), users.size() + numberBots);
    }

    @Test
    public void testGetGame() {
        Optional<Game> gameOptional = gameManagement.getGame(lobbyID);

        assertTrue(gameOptional.isPresent());
        assertEquals(gameDTO.getPlayers().get(0).getUser(), gameOptional.get().getPlayers().get(0).getUser());
    }

    @Test
    public void testGetProgramCards() {
        boolean callBot = gameManagement.getProgramCards(lobbyID);

        assertTrue(callBot);
    }


    @Test
    public void testGetPlayerReceivedCards() {
        boolean callBot = gameManagement.getProgramCards(lobbyID);
        assertTrue(callBot);

        List<CardDTO> playerReceivedCards = gameManagement.getPlayerReceivedCards(lobbyID, user);
        AbstractPlayer player = gameManagement.getGame(lobbyID).get().getPlayerByUserDTO(user);

        assertEquals(playerReceivedCards.size(), player.getReceivedCards().length);

        for (int i = 0; i < playerReceivedCards.size(); i++) {
            assertEquals(Optional.ofNullable(playerReceivedCards.get(i).getID()),
                    Optional.of(player.getReceivedCards()[i].getId()));
            assertEquals(playerReceivedCards.get(i).getPriority(),
                    player.getReceivedCards()[i].getPriority());
        }
    }

    @Test
    public void testGetPlayerDamageTokens() {
        int playerDamageTokens = gameManagement.getPlayerDamageTokens(lobbyID, user);

        assertEquals(playerDamageTokens,
                gameManagement.getGame(lobbyID).get().getPlayerByUserDTO(user).getRobot().getDamageToken());
    }

    @Test
    public void testSelectCardBot() throws InterruptedException {
        boolean callBot = gameManagement.getProgramCards(lobbyID);
        assertTrue(callBot);

        Pair<Boolean, List<AbstractLobbyMessage>> pair = gameManagement.selectCardBot(lobbyID);

        boolean allChosen = pair.getValue0();
        assertFalse(allChosen);

        List<AbstractLobbyMessage> msgs = pair.getValue1();
        assertNotNull(msgs);

        for (AbstractLobbyMessage msg: msgs ) {
            assertTrue("msg should be of type PlayerIsReadyMessage", msg instanceof PlayerIsReadyMessage);
        }
    }
}

