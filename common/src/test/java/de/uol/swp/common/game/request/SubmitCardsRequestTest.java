package de.uol.swp.common.game.request;

import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SubmitCardsRequestTest {

    private final UserDTO loggedInUser = new UserDTO("Player1", "pw", "ml");
    private final UserDTO notLoggedInUser = new UserDTO("Player2", "pw2", "ml2");
    private final UUID chatID = UUID.randomUUID();
    private LobbyDTO lobbyDTO =
            new LobbyDTO(123, "testLobby", loggedInUser, "pw", false, chatID, false);

    /**
     * Tests the constructor with a negative lobbyID
     *
     * @author WKempel
     * @result The constructor should throw a NullPointerException
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-06-15
     */
    @Test
    public void testConstructorWithNegativeLobbyID() {

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new SubmitCardsRequest(lobbyDTO.getLobbyID(), loggedInUser, null);
                });
    }

    /**
     * Tests the constructor with a null as value for the loggedInUser
     *
     * @author WKempel
     * @result The constructor should throw a NullPointerException
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-06-15
     */
    @Test
    public void testConstructorWithNullUserDTO() {

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new SubmitCardsRequest(lobbyDTO.getLobbyID(), loggedInUser, null);
                });
    }

    /**
     * Tests the constructor with a null as value for the cardDTOs
     *
     * @author WKempel
     * @result The constructor should throw a NullPointerException
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-06-15
     */
    @Test
    public void testConstructorWithNullCardDTOs() {

        Assertions.assertThrows(
                NullPointerException.class,
                () -> {
                    new SubmitCardsRequest(lobbyDTO.getLobbyID(), loggedInUser, null);
                });
    }

    /**
     * Tests the getSameCardDTOs method
     *
     * @result The method should return the same cardDTOs as the constructor
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-06-15
     */
    @Test
    public void testGetSameCardDTOs() {
        List<CardDTO> cardDTOs = new ArrayList<>();
        cardDTOs.add(new CardDTO(1, 10));
        cardDTOs.add(new CardDTO(2, 20));

        SubmitCardsRequest request =
                new SubmitCardsRequest(lobbyDTO.getLobbyID(), loggedInUser, cardDTOs);

        List<CardDTO> result = request.getCardDTOs();

        Assertions.assertEquals(cardDTOs, result);
    }

    /**
     * Tests the getNotSameCardDTOs method
     *
     * @author WKempel
     * @result The method should return not the same cardDTOs as the constructor
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-06-15
     */
    @Test
    public void testGetNotSameCardDTOs() {
        List<CardDTO> cardDTOs = new ArrayList<>();
        cardDTOs.add(new CardDTO(1, 10));
        List<CardDTO> cardDTOs2 = new ArrayList<>();
        cardDTOs2.add(new CardDTO(2, 20));

        SubmitCardsRequest request =
                new SubmitCardsRequest(lobbyDTO.getLobbyID(), loggedInUser, cardDTOs);
        SubmitCardsRequest request2 =
                new SubmitCardsRequest(lobbyDTO.getLobbyID(), loggedInUser, cardDTOs2);

        Assertions.assertNotSame(request.getCardDTOs(), request2.getCardDTOs());
    }

    /**
     * Tests the getLobbyID method
     *
     * @author WKempel
     * @result The method should return the getLobbyID
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-06-15
     */
    @Test
    public void testGetLobbyID() {

        List<CardDTO> cardDTOs = new ArrayList<>();

        SubmitCardsRequest request =
                new SubmitCardsRequest(lobbyDTO.getLobbyID(), loggedInUser, cardDTOs);

        int result = request.getLobbyID();

        Assertions.assertEquals(lobbyDTO.getLobbyID(), result);
    }

    /**
     * Tests getLoggedInUser method
     *
     * @author WKempel
     * @result The method should return the loggedInUser
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-06-15
     */
    @Test
    public void testGetLoggedInUser() {

        List<CardDTO> cardDTOs = new ArrayList<>();

        SubmitCardsRequest request =
                new SubmitCardsRequest(lobbyDTO.getLobbyID(), loggedInUser, cardDTOs);
        SubmitCardsRequest request2 =
                new SubmitCardsRequest(lobbyDTO.getLobbyID(), notLoggedInUser, cardDTOs);

        UserDTO result = request.getLoggedInUser();

        Assertions.assertEquals(loggedInUser, result);
        Assertions.assertNotSame(request2.getLoggedInUser(), result);
    }

    /**
     * Tests the authorizationNeeded method
     *
     * @author WKempel
     * @result The method should return true
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-06-15
     */
    @Test
    public void testAuthorizationNeeded() {
        List<CardDTO> cardDTOs = new ArrayList<>();

        SubmitCardsRequest request =
                new SubmitCardsRequest(lobbyDTO.getLobbyID(), loggedInUser, cardDTOs);
        boolean result = request.authorizationNeeded();

        Assertions.assertTrue(result);
    }
}
