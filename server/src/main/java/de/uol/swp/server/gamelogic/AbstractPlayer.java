package de.uol.swp.server.gamelogic;

import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.cards.Card;

/**
 * @author Maria Eduarda Costa Leite Andrade
 * @see de.uol.swp.server.gamelogic.cards.Card
 * @since 2023-03-28
 */
public abstract class AbstractPlayer {
    protected Robot robot;
    protected Card[] receivedCards;
    protected Card[] chosenCards;

    protected UserDTO userDTO;
    protected int numCardToPlay = 0;


    /** Constructor
     */
    public AbstractPlayer() {}

    /**
     * Player receives 9 or fewer cards
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-03-28
     */
    public void receiveCards(Card[] cardsToReceive) {
        this.receivedCards = cardsToReceive;
    }

    /**
     * Player is ready
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-03-28
     */
    public void chooseCardsOrder(Card[] chosenCards) {
        this.chosenCards = chosenCards;
    }

    /**
     * Get Received Cards
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-03-28
     */
    public Card[] getReceivedCards() {
        return this.receivedCards;
    }

    /**
     * Get Choosen Cards
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-03-28
     */
    public Card[] getChosenCards() {
        return this.chosenCards;
    }

    /**
     * Get Robot
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-06
     */
    public Robot getRobot() {
        return this.robot;
    }

    /**
     * Return next card to be played
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-03-28
     */
    public Card playCard() {
        Card nextCard = this.chosenCards[numCardToPlay];
        numCardToPlay += 1;
        return nextCard;
    }

    /**
     * @author
     * @since
     */
    public UserDTO getUser() {
        return userDTO;
    }

    /**
     * @author
     * @since
     */
    public void setUser(UserDTO user) {
        this.userDTO = user;
    }

    /**
     * @author
     * @since
     */
    public void setReceivedCards(Card[] receivedCards) {
        this.receivedCards = receivedCards;
    }

    /**
     * @author
     * @since
     */
    public int getNumCardToPlay() {
        return numCardToPlay;
    }

    /**
     * @author
     * @since
     */
    public void setNumCardToPlay(int numCardToPlay) {
        this.numCardToPlay = numCardToPlay;
    }
}
