package de.uol.swp.common.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import de.uol.swp.common.game.dto.CardDTO;

import org.junit.jupiter.api.Test;

public class CardDTOTest {

    @Test
    void getIDTest() {
        Integer expectedCardID = 123;
        Integer notExpectedCardID = 321;
        int priority = 1;
        int priority2 = 2;
        CardDTO cardDTO = new CardDTO(expectedCardID, priority);
        CardDTO cardDTO2 = new CardDTO(notExpectedCardID, priority2);

        assertEquals(expectedCardID, cardDTO.getID());
        assertNotEquals(expectedCardID, cardDTO2.getID());
    }

    @Test
    void getPriorityTest() {
        Integer expectedPriorityHigher = 123;
        Integer notExpectedPriorityLower = 321;
        int priority = 10;
        int priority2 = 2;
        CardDTO cardDTO = new CardDTO(expectedPriorityHigher, priority);
        CardDTO cardDTO2 = new CardDTO(notExpectedPriorityLower, priority2);

        assertEquals(priority, cardDTO.getPriority());
        assertNotEquals(priority, cardDTO2.getPriority());
    }
}