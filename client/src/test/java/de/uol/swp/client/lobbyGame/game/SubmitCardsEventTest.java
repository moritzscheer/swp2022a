package de.uol.swp.client.lobbyGame.game;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.uol.swp.client.lobbyGame.game.events.SubmitCardsEvent;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Test SubmitCardsEventTest
 *
 * @author WKempel
 * @see SubmitCardsEventTest
 * @since 2023-06-26
 */
public class SubmitCardsEventTest {

    private final UserDTO user = new UserDTO("player1", "pw1", "ml1");
    private final UUID uuid = UUID.randomUUID();
    private final LobbyDTO lobbyDTO = new LobbyDTO(123, "testLobby", user, "pw", true, uuid, false);

    private final List<CardDTO> cards = new ArrayList<>();

    @BeforeEach
    public void setup() {
        cards.add(new CardDTO(1, 10));
        cards.add(new CardDTO(2, 10));
        cards.add(new CardDTO(3, 10));
        cards.add(new CardDTO(4, 10));
        cards.add(new CardDTO(5, 10));
    }

    @Test
    public void testConstructorAndGetters() {
        SubmitCardsEvent event = new SubmitCardsEvent(lobbyDTO.getLobbyID(), user, cards);

        assertEquals(lobbyDTO.getLobbyID(), event.getLobbyID());
        assertEquals(cards, event.getCardDTOS());
        assertEquals(user, event.getLoggedInUser());
    }
}
