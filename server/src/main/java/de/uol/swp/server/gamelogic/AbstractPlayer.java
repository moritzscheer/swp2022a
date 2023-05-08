package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.cards.Card;

/**
 * @author Maria Eduarda Costa Leite Andrade
 * @see de.uol.swp.server.gamelogic.cards.Card
 * @since 2023-03-28
 */
public abstract class AbstractPlayer {
    protected Robot robot;
    protected Card[] receivedCards;
    protected Card[] chosenCards = new Card[5];
    protected int numCardToPlay = 0;

    public AbstractPlayer(Robot robot, Card[] cards) {
        this.robot = robot;
        this.receivedCards = cards;
    }

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
}