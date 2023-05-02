package de.uol.swp.common.game.dto;

/**
 * Object to transfer the information of a game card
 *
 * <p>This object is used to communicate the current state of game cards between the server and
 * clients. It contains information about the id of the card, what the card does and its priority
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-05-02
 */
public class CardDTO implements Card {
    private final Integer cardID;
    private final int priority;

    /**
     * Constructor
     *
     * @param cardID The id of the card
     * @param priority The priority of the card
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-02
     */
    public CardDTO(Integer cardID, int priority) {
        this.cardID = cardID;
        this.priority = priority;
    }

    @Override
    public Integer getID() {
        return this.cardID;
    }

    @Override
    public int getPriority() {
        return this.priority;
    }
}
