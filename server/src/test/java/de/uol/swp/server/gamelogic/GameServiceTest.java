package de.uol.swp.server.gamelogic;

import com.google.common.eventbus.EventBus;
import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.request.GetProgramCardsRequest;
import de.uol.swp.common.game.request.StartGameRequest;
import de.uol.swp.common.game.request.SubmitCardsRequest;
import de.uol.swp.common.game.request.TurnRobotOffRequest;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.request.CreateLobbyRequest;
import de.uol.swp.common.lobby.request.JoinLobbyRequest;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.chat.TextChatService;
import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.lobby.LobbyManagement;
import de.uol.swp.server.lobby.LobbyService;
import de.uol.swp.server.usermanagement.AuthenticationService;
import de.uol.swp.server.usermanagement.UserManagement;
import de.uol.swp.server.usermanagement.store.MainMemoryBasedUserStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static de.uol.swp.server.utils.ConvertToDTOUtils.convertCardsToCardsDTO;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Test GameServiceTest
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-07-07
 */
public class GameServiceTest {

    static final EventBus bus = new EventBus();
    static final AuthenticationService authenticationService =
            new AuthenticationService(bus, new UserManagement(new MainMemoryBasedUserStore()));
    LobbyManagement lobbyManagement = new LobbyManagement();
    final LobbyService lobbyService = new LobbyService(lobbyManagement, authenticationService, bus);

    GameManagement gameManagement = new GameManagement();
    final GameService gameService = new GameService(bus, gameManagement, lobbyManagement, lobbyService);

    static final UserDTO user = new UserDTO("Marco", "Marco2", "Marco2@Grawunder.com");
    static final UserDTO user2 = new UserDTO("Maria", "Maria", "maria@mail.com");
    static final TextChatService textChatService = new TextChatService(authenticationService, bus);

    static UUID textChannelUUID = textChatService.createTextChatChannel();
    int lobbyID;
    LobbyDTO lobby;


    // ------------------------------------------
    // onStartGameRequest tests
    // ------------------------------------------

    @BeforeEach
    public void setUp(){
        final CreateLobbyRequest request = new CreateLobbyRequest("lobby1", user, true, "password");
        bus.post(request);

        lobbyID = lobbyManagement.getLobbies().get(0).getLobbyID();

        final JoinLobbyRequest request1 = new JoinLobbyRequest(lobbyID, "lobby1", user2, "password");
        bus.post(request1);

        lobby = lobbyManagement.getLobby(lobbyID).get();
        lobby.setMapName("MapOne");

        final StartGameRequest request2 = new StartGameRequest(lobby, 2, 2);
        bus.post(request2);
    }

    /**
     * Tests if a game can be created
     *
     * @author Maria
     * @since 2023-07-07
     */
    @Test
    public void testCreateGame() {
        assertNotNull(gameManagement.getGame(lobbyID));
        assertEquals(1, gameManagement.getGame(lobbyID).get().getRoundNumber());
        assertEquals(4, gameManagement.getGame(lobbyID).get().getPlayers().size());
    }

    /**
     * Tests distribute cards
     *
     * @author Maria
     * @since 2023-07-07
     */
    @Test
    public void testGetCards() {
        final GetProgramCardsRequest request = new GetProgramCardsRequest(lobbyID, user);
        bus.post(request);

        assertNotNull(gameManagement.getGame(lobbyID).get().getPlayers().get(0).getReceivedCards());

        // and two bots
        assertNotNull(gameManagement.getGame(lobbyID).get().getPlayers().get(1).getReceivedCards());
        assertNotNull(gameManagement.getGame(lobbyID).get().getPlayers().get(2).getReceivedCards());
    }

    /**
     * Tests distribute cards
     *
     * @author Maria
     * @since 2023-07-07
     */
    @Test
    public void testTurnOff() {

        Position oldPos = gameManagement.getGame(lobbyID).get().getPlayers().get(0).getRobot().getPosition();
        final TurnRobotOffRequest request = new TurnRobotOffRequest(lobbyID, user);
        bus.post(request);

        assertTrue(gameManagement.getGame(lobbyID).get().getPlayers().get(0).getRobot().isPowerDown());
    }


    /**
     * Tests distribute cards
     *
     * @author Maria
     * @since 2023-07-07
     */
    @Test
    public void testSubmitCardsRequest() {
        final GetProgramCardsRequest request3 = new GetProgramCardsRequest(lobbyID, user);
        bus.post(request3);

        Card[] receivedCards = gameManagement.getGame(lobbyID).get().getPlayers().get(0).getReceivedCards();
        List<CardDTO> cardDTO = convertCardsToCardsDTO(Arrays.copyOfRange(receivedCards, 0, 5));

        final SubmitCardsRequest request = new SubmitCardsRequest(lobbyID, user, cardDTO);
        bus.post(request);

        Card[] cards = gameManagement.getGame(lobbyID).get().getPlayers().get(0).getChosenCards();

        for(int i = 0; i < 5; i++){
            assertEquals(receivedCards[i].getId(), cards[i].getId());
            assertEquals(receivedCards[i].getPriority(), cards[i].getPriority());
        }

    }


}
