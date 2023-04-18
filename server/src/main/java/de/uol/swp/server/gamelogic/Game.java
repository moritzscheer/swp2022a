package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

/**
 * @author
 * @see
 * @since
 */
public class Game {

    private Block[][] board;

    // TODO: Remove dockingBays field
    private Position[] dockingBays;
    private Robot[] robots;
    private int nRobots;
    private int rowCount;
    private int columCount;
    private int programStep;
    private Timer timer;
    private int readyRegister;
    private Player[] players;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.Block
     * @see de.uol.swp.server.gamelogic.Position
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 20-02-2023
     */
    public Game(Block[][] board, Position[] dockingBays, Robot[] robots, Player[] players) {
        this.board = board;
        this.dockingBays = dockingBays;
        this.robots = robots;
        this.nRobots = robots.length;
        this.rowCount = board.length;
        this.columCount = board[0].length;
        this.programStep = 0;
        this.players = players;
    }

    /**
     * Set docking bays, might not be necessary if only set in constructor
     *
     * @author Maria
     * @since 2023-04-18
     */
    public void setDockingBaysPositions(Position[] positions) {
        this.dockingBays = positions;
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
     * Get next card of a player
     *
     * @author Maria
     * @see de.uol.swp.server.gamelogic.Player
     * @since 2023-04-18
     */
    public Card revealProgramCards(int playerIndex) {
        return this.players[playerIndex].playCard();
    }

    /**
     * @author
     * @see
     * @since
     */
    public void startTimer(int readyRegister, int nRobots) {
        // TODO
    }

    private void calcGameRound(Card[][] playedCards) {
        // Iterate through the 5 cards
        if (playedCards[0].length != 5) {
            // TODO: Log Error regarding card count
        }
        for (int cardIterator = 0; cardIterator < playedCards[0].length; cardIterator++) {
            // Iterate through the X card of all Players and resolve them
            for (int playerIterator = 0; playerIterator < playedCards.length; playerIterator++) {
                List<List<MoveIntent>> moves;
                moves = resolveCard(playedCards[playerIterator][cardIterator], playerIterator);
                for (List<MoveIntent> move : moves) {
                    List<MoveIntent> resolvedMoves = resolveMoveIntentConflicts(move);
                    executeMoveIntents(resolvedMoves);
                }
            }
            // Iterate through all the traps
            for (Block[] blocksX : board) {
                for (Block blockXY : blocksX) {
                    List<MoveIntent> moves;

                    //TODO: implementation of ActionReports for use in a GameMoveHistory
                    //Preferably altering the behaviour Methods to return (or get as parameters)
                    //the list of ActionReports and MoveIntents

                    moves = blockXY.OnExpressConveyorStage(cardIterator);
                    moves = resolveMoveIntentConflicts(moves);
                    executeMoveIntents(moves);

                    moves = blockXY.OnConveyorStage(cardIterator);
                    moves = resolveMoveIntentConflicts(moves);
                    executeMoveIntents(moves);

                    moves = blockXY.OnPusherStage(cardIterator);
                    moves = resolveMoveIntentConflicts(moves);
                    executeMoveIntents(moves);

                    moves = blockXY.OnRotatorStage(cardIterator);
                    moves = resolveMoveIntentConflicts(moves);
                    executeMoveIntents(moves);

                    moves = blockXY.OnPresserStage(cardIterator);
                    moves = resolveMoveIntentConflicts(moves);
                    executeMoveIntents(moves);

                    moves = blockXY.OnLaserStage(cardIterator);
                    moves = resolveMoveIntentConflicts(moves);
                    executeMoveIntents(moves);

                    moves = blockXY.OnCheckPointStage(cardIterator);
                    moves = resolveMoveIntentConflicts(moves);
                    executeMoveIntents(moves);
                }
            }
        }
        // Send back a collective result of the whole GameRound
    }

    private List<List<MoveIntent>> resolveCard(Card card, int robotID) {
        List<List<MoveIntent>> moves = new ArrayList<>();
        //TODO: handle rotations
        for (int i = 0; i < 3/*card.move() TODO: modify card.move() to return the number of moves*/; i++) {
            List<MoveIntent> subMoveList = new ArrayList<>();
            subMoveList.add(new MoveIntent(robotID, robots[robotID].getDirection()));
            moves.add(subMoveList);
        }
        return moves;
    }

    private void executeMoveIntents(List<MoveIntent> moves) {
        if (moves != null) {
            for (MoveIntent move : moves) {
                robots[move.robotID].move(move.direction);
            }
        }
    }

    public List<MoveIntent> resolveMoveIntentConflicts(List<MoveIntent> movesIn) {
        ArrayList<MoveResult> moveList = new ArrayList<>();

        if (movesIn == null) {
            throw new IllegalArgumentException(
                    "The list of moves should not be null.  (But can contain zero elements.)");
        }

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
            somethingChanged = removeWallIntersections(moveList, somethingChanged, board);

            // remove head on collisions
            somethingChanged = removeHeadOnCollisions(moveList, somethingChanged);

            // remove same destination collisions
            somethingChanged = removeSameDestinationConflicts(moveList, somethingChanged);

            // add moves for pushed robots
            somethingChanged = addPushMoves(moveList, somethingChanged);

        } while (somethingChanged);

        return (moveList.stream()
                .map((MoveResult value) -> (MoveIntent) value)
                .collect(Collectors.toList()));
    }

    private boolean addPushMoves(ArrayList<MoveResult> moveList, boolean somethingChanged) {
        for (int i = 0; i < moveList.size(); i++) {
            MoveResult move = moveList.get(i);
            Position currentTile = move.getOriginPosition();
            Position destinationTile = move.getTargetPosition();
            CardinalDirection moveDir = move.getDirection();

            for (int j = 0; j < robots.length; j++) {
                Robot robot = robots[j];
                if (!robot.equals(robots[move.robotID])) {
                    if (robot.getPosition() == destinationTile) {
                        boolean alreadyHasMoveIntent = false;
                        for (MoveResult moveResult : moveList) {
                            if (robot.equals(robots[moveResult.robotID])) {
                                alreadyHasMoveIntent = true;
                                break;
                            }
                        }
                        if (!alreadyHasMoveIntent) {
                            moveList.add(new MoveResult(move, j));
                            i = -1;
                            somethingChanged = true;
                        }
                    }
                }
            }
        }
        return somethingChanged;
    }

    private static boolean removeSameDestinationConflicts(
            ArrayList<MoveResult> moveList, boolean somethingChanged) {
        for (int i = 0; i < moveList.size(); i++) {
            MoveResult move = moveList.get(i);
            Position currentTile = move.getOriginPosition();
            Position destinationTile = move.getTargetPosition();
            CardinalDirection moveDir = move.getDirection();

            for (int j = 0; j < moveList.size(); j++) {
                if (i != j) {
                    if (destinationTile.equals(moveList.get(j).getTargetPosition())) {
                        removeMoveResultAndParents(move, moveList);
                        removeMoveResultAndParents(moveList.get(j), moveList);
                        i = -1;
                        j = -1;
                        somethingChanged = true;
                    }
                }
            }
        }
        return somethingChanged;
    }

    private static boolean removeWallIntersections(
            ArrayList<MoveResult> moveList, boolean somethingChanged, Block[][] board) {
        for (int i = 0; i < moveList.size(); i++) {
            MoveResult move = moveList.get(i);
            Position currentTile = move.getOriginPosition();
            Position destinationTile = move.getTargetPosition();
            CardinalDirection moveDir = move.getDirection();

            if (checkForObstruction(currentTile, destinationTile, moveDir, board)) {
                removeMoveResultAndParents(move, moveList);
                i = -1;
                somethingChanged = true;
            }
        }
        return somethingChanged;
    }

    private static boolean removeHeadOnCollisions(
            ArrayList<MoveResult> moveList, boolean somethingChanged) {
        for (int i = 0; i < moveList.size(); i++) {
            MoveResult move = moveList.get(i);
            Position currentTile = move.getOriginPosition();
            Position destinationTile = move.getTargetPosition();
            CardinalDirection moveDir = move.getDirection();

            for (int j = 0; j < moveList.size(); j++) {
                if (i != j) {
                    if (moveDir
                                    == CardinalDirection.values()[
                                            moveList.get(j).getDirection().ordinal() + 2]
                            && destinationTile == moveList.get(j).getOriginPosition()) {
                        removeMoveResultAndParents(move, moveList);
                        removeMoveResultAndParents(moveList.get(j), moveList);
                        i = -1;
                        j = -1;
                        somethingChanged = true;
                    }
                }
            }
        }
        return somethingChanged;
    }

    private static boolean checkForObstruction(
            Position currentTile,
            Position destinationTile,
            CardinalDirection moveDir,
            Block[][] board) {
        return board[currentTile.x][currentTile.y].getObstruction(moveDir)
                || board[destinationTile.x][destinationTile.y].getObstruction(
                        CardinalDirection.values()[moveDir.ordinal() + 2]);
    }

    private static void removeMoveResultAndParents(
            MoveResult move, ArrayList<MoveResult> moveList) {
        while (moveList.contains(move)) {
            moveList.remove(move);
            if (move.parentMove != null) {
                move = move.parentMove;
            }
        }
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

        public Position getTargetPosition() {
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

        public Position getOriginPosition() {
            return robots[robotID].getPosition();
        }
    }
}
