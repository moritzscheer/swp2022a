package de.uol.swp.server.gamelogic;

import java.util.Objects;

/**
 * @author
 * @see
 * @since
 */
public class Position {
    // TODO
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
