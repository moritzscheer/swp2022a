package de.uol.swp.server.gamelogic;

import java.util.Objects;

/**
 *
 *
 * @author
 * @see
 * @since
 */
public class Position {
    //TODO
    public int x;
    public int y;

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
