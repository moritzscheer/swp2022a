package de.uol.swp.common.game.dto;

import java.util.Objects;

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
    /**
     * Getter for the cardID
     *
     * @return Returns an integer who represents the cardID
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-02
     */
    @Override
    public Integer getID() {
        return this.cardID;
    }
    /**
     * Getter for the Priority
     *
     * @return Returns an integer who represents the priority
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-02
     */
    @Override
    public int getPriority() {
        return this.priority;
    }
    /**
     * Override for equals method to compare not only the object but the object's attributes
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-07-03
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDTO cardDTO = (CardDTO) o;
        return Objects.equals(cardID, cardDTO.cardID) && Objects.equals(priority, cardDTO.priority);
    }
}
