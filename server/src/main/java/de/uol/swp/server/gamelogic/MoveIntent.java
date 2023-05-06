package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

/**
 * Map Builder
 *
 * @author Finn Oldeboershuis
 * @since 2023-02-03
 */
public class MoveIntent {
    public final String robotID;
    public final CardinalDirection direction;

    public MoveIntent(String id, CardinalDirection dir) {
        this.robotID = id;
        this.direction = dir;
    }

    public CardinalDirection getDirection() {
        return this.direction;
    }
}
