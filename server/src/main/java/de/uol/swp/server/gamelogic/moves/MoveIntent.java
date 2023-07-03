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
     * @author
     * @since
     */
    public MoveIntent(int id, CardinalDirection dir) {
        this.robotID = id;
        this.direction = dir;
    }

    /**
     * @author
     * @since
     */
    public CardinalDirection getDirection() {
        return this.direction;
    }
}
