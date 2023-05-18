package de.uol.swp.common.game;

import de.uol.swp.common.game.enums.CardinalDirection;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Maria Andrade & Finn Oldeboershuis
 * @see
 * @since 2023-03-17
 */
public final class Position implements Serializable {
    public int x;
    public int y;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 20-02-2023
     */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Position translate(Position p, CardinalDirection d) {
        switch (d) {
            case East:
                return new Position(p.x + 1, p.y);
            case West:
                return new Position(p.x - 1, p.y);
            case North:
                return new Position(p.x, p.y - 1);
            case South:
                return new Position(p.x, p.y + 1);
            default:
                return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
