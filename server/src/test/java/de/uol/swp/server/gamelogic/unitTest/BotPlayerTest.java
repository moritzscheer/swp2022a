package de.uol.swp.server.gamelogic.unitTest;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.AbstractPlayer;
import de.uol.swp.server.gamelogic.BotPlayer;
import org.junit.jupiter.api.Test;

public class BotPlayerTest {


    private final Position position = new Position(0, 0);
    private int robotID = 0;
    private UserDTO userDTO = new UserDTO("Bot1", "password", "someEmail");

    private AbstractPlayer botPlayer = new BotPlayer(position, robotID);

    @Test
    public void testBotPlayerConstructor() {

        assert(botPlayer.getUser().getUsername().equals("Bot1"));
        assert(botPlayer.getUser().getEMail().equals("someEmail"));
        assert(botPlayer.getUser().getPassword().equals("password"));
        assert(botPlayer.getRobot().getID() == 0);
        assert(botPlayer.getRobot().getPosition().equals(position));
        assert(botPlayer.getRobot().getDirection().equals(CardinalDirection.North));
    }

    @Test
    public void testBotPlayerConstructorWithTwoBots() {

        for(int i = 0; i < 2; i++) {
            botPlayer = new BotPlayer(position, robotID);
            assert(botPlayer.getUser().getUsername().equals("Bot" + (i + 1)));
            assert(botPlayer.getUser().getEMail().equals("someEmail"));
            assert(botPlayer.getUser().getPassword().equals("password"));
            assert(botPlayer.getRobot().getID() == i);
            assert(botPlayer.getRobot().getPosition().equals(position));
            assert(botPlayer.getRobot().getDirection().equals(CardinalDirection.North));
            robotID++;
        }
    }
}