package de.uol.swp.server.gamelogic;

import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.cards.Card;

/**
 * @author Maria Eduarda Costa Leite Andrade
 * @see de.uol.swp.server.gamelogic.cards.Card
 * @see de.uol.swp.server.gamelogic.Robot
 * @since 2023-03-28
 */
public class BotPlayer extends AbstractPlayer {

    public BotPlayer(Robot robot, Card[] cards) {
        super();
        userDTO = new UserDTO("Bot", "passwort", "mail");
    }

    // TODO

}
