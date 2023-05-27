package de.uol.swp.common.game.dto;

import java.io.Serializable;

/**
 * Interface to unify card objects
 *
 * <p>This is an Interface to allow for multiple types of cards objects since it is possible that
 * not every client has to have every information of the card.
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @see de.uol.swp.common.game.dto.CardDTO
 * @since 2023-05-02
 */
public interface Card extends Serializable {
    /**
     * Getter for the cardID
     *
     * @return An Integer containing the ID of the card
     * @since 2023-05-02
     */
    Integer getID();

    /**
     * Getter for the card priority
     *
     * @return An int containing the priority of the card
     * @since 2023-05-02
     */
    int getPriority();
}
