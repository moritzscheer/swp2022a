package de.uol.swp.common.game.response;

import de.uol.swp.common.game.dto.CardDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProgramCardDataResponseTest {

    private final List<CardDTO> cards = new ArrayList<>();
    private final int freeCards = 0;
    private final int lobbyID = 123;

    /**
     * Sets up the test
     *
     * @author WKempel
     * @since 2023-06-27
     */
    @BeforeEach
    public void setup() {
        cards.add(new CardDTO(1, 10));
        cards.add(new CardDTO(2, 20));
        cards.add(new CardDTO(3, 30));
        cards.add(new CardDTO(4, 40));
        cards.add(new CardDTO(5, 50));

    }

    /**
     * Tests the constructor and getters
     *
     * @author WKempel
     * @result The constructor should return the correct values like cards, freeCards and lobbyID
     * @see de.uol.swp.common.game.response.ProgramCardDataResponse
     * @since 2023-06-27
     */
    @Test
    public void testConstructorAndGetters() {
        ProgramCardDataResponse programCardDataResponse = new ProgramCardDataResponse(cards, freeCards, lobbyID);

       assertEquals(cards, programCardDataResponse.getAssignedProgramCards());
       assertEquals(freeCards, programCardDataResponse.getFreeCards());
       assertEquals(lobbyID, programCardDataResponse.getLobbyID());
    }
}
