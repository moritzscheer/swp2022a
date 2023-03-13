package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * @author
 * @see
 * @since
 */
public class Game {

    private Block[][] board;
    private Position[] dockingBays;
    private Robot[] robots;
    private int nRobots;
    private int rowCount;
    private int columCount;
    private int programStep;
    private Timer timer;
    private int readyRegister;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.Block
     * @see de.uol.swp.server.gamelogic.Position
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 20-02-2023
     */
    public Game(Block[][] board, Position[] dockingBays, Robot[] robots) {

        this.board = board;
        this.dockingBays = dockingBays;
        this.robots = robots;
        this.nRobots = robots.length;
        this.rowCount = board.length;
        this.columCount = board[0].length;
        this.programStep = 0;
    }

    /**
     * @author
     * @see
     * @since
     */
    public void setDockingBaysPositions(Position[] position) {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void register() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void revealProgramCards() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void moveRobots() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void moveBoardElements() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void fireLaser() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void touchCheckPoints() {
        // TODO
    }

    /**
     * @author
     * @see
     * @since
     */
    public void startTimer(int readyRegister, int nRobots) {
        // TODO
    }

    public List<MoveIntent> resolveMoveIntentConflicts(List<MoveIntent> movesIn) {
        ArrayList<MoveResult> moveList = new ArrayList<>();

        // convert every MoveIntent to a MoveResult
        for (MoveIntent move : movesIn) {
            moveList.add(new MoveResult(move));
        }

        // repeat the solving of conflicts until no more are left (by deleting the moves that do
        // cause them)
        boolean somethingChanged = false;
        do {
            somethingChanged = false;

            // remove moves that hit obstructions
            for (int i = 0; i < moveList.size(); i++) {
                MoveResult move = moveList.get(i);
                Position currentTile = move.getOriginPosition();
                Position destinationTile = move.getTargetPosition();
                CardinalDirection moveDir = move.getDirection();

                if (board[currentTile.x][currentTile.y].getObstruction(moveDir)
                        || board[currentTile.x][currentTile.y].getObstruction(
                                CardinalDirection.values()[moveDir.ordinal() + 2])) {}
            }

            // remove head on collisions

            // remove same destination collisions

            // add moves for pushed robots

        } while (somethingChanged);

        return (List<MoveIntent>) (List<?>) moveList;
    }

    /** @author Finn */
    private class MoveResult extends MoveIntent {

        public final MoveResult parentMove;

        public MoveResult(MoveIntent intent) {
            super(intent.robotID, intent.direction);
            parentMove = null;
        }

        public MoveResult(MoveResult parentMove, int robotID) {
            super(robotID, parentMove.direction);
            this.parentMove = parentMove;
        }

        public de.uol.swp.server.gamelogic.Position getTargetPosition() {
            Position p = robots[robotID].getPosition();
            switch (direction) {
                case East:
                    return new Position(p.x + 1, p.y);
                case West:
                    return new Position(p.x - 1, p.y);
                case South:
                    return new Position(p.x, p.y + 1);
                case North:
                    return new Position(p.x, p.y - 1);
            }
            throw new IllegalStateException();
        }

        public de.uol.swp.server.gamelogic.Position getOriginPosition() {
            return robots[robotID].getPosition();
        }
    }
}
