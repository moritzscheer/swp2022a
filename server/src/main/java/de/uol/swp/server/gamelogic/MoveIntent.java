package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

public class MoveIntent {
    public final int robotID;
    public final CardinalDirection direction;
    public final int moves;

    public MoveIntent(int id, CardinalDirection dir) {
        this.robotID = id;
        this.direction = dir;
        this.moves = 1;
    }

    public MoveIntent(int id, CardinalDirection dir, int moves) {
        this.robotID = id;
        this.direction = dir;
        this.moves = moves;
    }

    public CardinalDirection getDirection() {
        return this.direction;
    }

    public int getMoves() {
        return this.moves;
    }
}
