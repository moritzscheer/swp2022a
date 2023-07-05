package de.uol.swp.server.gamelogic.moves;

import de.uol.swp.common.game.enums.CardinalDirection;

/**
 * Map Builder
 *
 * @author Finn Oldeboershuis
 * @since 2023-02-03
 */
public class MoveIntent {
    public final int robotID;
    public final CardinalDirection direction;

    /**
     * @author Maria
     * @since 2023-04-22
     */
    public MoveIntent(int id, CardinalDirection dir) {
        this.robotID = id;
        this.direction = dir;
    }

    /**
     * @author Finn
     * @since 2023-03-06
     */
    public CardinalDirection getDirection() {
        return this.direction;
    }
}
