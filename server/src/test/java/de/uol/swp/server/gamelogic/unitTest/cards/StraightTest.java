package de.uol.swp.server.gamelogic.unitTest.cards;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.Game;
import de.uol.swp.server.gamelogic.Robot;
import de.uol.swp.server.gamelogic.cards.Straight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class StraightTest {

    @Test
    void testGetMoves() {
        Straight straight = new Straight(2);
        Assertions.assertEquals(2, straight.getMoves());
    }
}
