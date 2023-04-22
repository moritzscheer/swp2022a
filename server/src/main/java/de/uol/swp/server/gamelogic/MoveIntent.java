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

    /**
     * Constructor with moves
     *
     * <p>This is necessary to handle movements with cards, which may move 1, 2 or 3 positions
     *
     * @author Maria Eduarda
     * @since 2023-04-22
     */
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
