package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

public class MoveIntent {
    public final int robotID;
    public final CardinalDirection direction;

    public MoveIntent(int id, CardinalDirection dir) {
        robotID = id;
        direction = dir;
    }

    public CardinalDirection getDirection() {
        return direction;
    }
}
