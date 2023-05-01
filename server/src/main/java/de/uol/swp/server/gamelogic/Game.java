package de.uol.swp.server.gamelogic;

import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.cards.Direction;
import de.uol.swp.server.gamelogic.tiles.enums.CardinalDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author
 * @see
 * @since
 */
public class Game {

    private final Block[][] board;

    // TODO: Remove dockingBays field
    private Position[] dockingBays;
    private final Robot[] robots;
    private int nRobots;
    private int rowCount;
    private int columnCount;
    private int columCount;
    private int programStep; // program steps from 1 to 5
    private Timer timer;
    private int readyRegister; // count how many are ready
    private final AbstractPlayer[] players;
    private final Card[][] playedCards;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.Block
     * @see de.uol.swp.server.gamelogic.Position
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 20-02-2023
     */
    public Game(
            Block[][] board,
            Position[] dockingBays,
            Robot[] robots,
            Timer timer,
            AbstractPlayer[] players) {
        this.board = board;
        this.dockingBays = dockingBays;
        this.robots = robots;
        this.nRobots = robots.length;
        this.rowCount = board.length;
        this.columnCount = board[0].length;
        this.timer = timer;
        this.programStep = 0;
        this.players = players;
        this.readyRegister = 0;
        this.playedCards = new Card[nRobots][5];
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
     * When a player has chosen its cards, he will press "register" button this function will call
     * the player function that will register his cards Once all players have chosen, the calcGame
     * will be called
     *
     * @author Maria
     * @see de.uol.swp.server.gamelogic.Player
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-04-25
     */
    public void register(AbstractPlayer playerIsReady, Card[] playerCards)
            throws InterruptedException {
        // TODO
        // check when all players are ready to register the next cards
        this.readyRegister += 1;
        playerIsReady.chooseCardsOrder(playerCards); // set cards of this player

        if (this.readyRegister == this.nRobots - 1) {
            startTimer();
        } else if (this.readyRegister == this.nRobots) {
            this.programStep = 1; // start in the first program step, until 5
            for (int playerIterator = 0; playerIterator < players.length; playerIterator++) {
                this.playedCards[playerIterator] = players[playerIterator].getChosenCards();
            }
            revealProgramCards();
            goToNextRound();
            this.readyRegister = 0;
        }
    }

    /**
     * Sends response to client with cards to be displayed
     *
     * @author Maria
     * @see de.uol.swp.server.gamelogic.Player
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-04-25
     */
    public Card[] revealProgramCards() {
        // TODO: send message to client

        Card[] cardsInThisProgramStep = new Card[this.nRobots];
        for (int playerIterator = 0; playerIterator < players.length; playerIterator++) {
            cardsInThisProgramStep[playerIterator] =
                    this.playedCards[playerIterator][this.programStep];
        }
        return cardsInThisProgramStep;
    }

    /**
     * @author
     * @see
     * @since
     */
    public void startTimer() {
        // TODO
    }

    /**
     * This function call each one of the five steps of one round
     *
     * <p>it increments this.programStep and call calcGameRound
     *
     * @author Maria
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-04-25
     */
    public void goToNextRound() throws InterruptedException {
        // TODO
        while (this.programStep <= 5) {
            calcGameRound();
            // sleep
            TimeUnit.SECONDS.sleep(30);
            // go to next step
            this.programStep += 1;
        }
        // round is over
        this.programStep = 0;
    }

    private void calcGameRound() {
        // Iterate through the 5 cards
        if (this.playedCards[0].length != 5) {
            // TODO: Log Error regarding card count
        }
        // TODO: row is Player, column is card
        // idea: you can iterate over the players with:
        // this.playedCards[playerIterator][this.programStep]
        // programStep changes in goToNextRound(cards)

        // Iterate through the X card of all Players and resolve them
        for (int playerIterator = 0; playerIterator < this.playedCards.length; playerIterator++) {
            List<List<MoveIntent>> moves;
            moves = resolveCard(this.playedCards[playerIterator][this.programStep], playerIterator);
            for (List<MoveIntent> move : moves) {
                List<MoveIntent> resolvedMoves = resolveMoveIntentConflicts(move);
                executeMoveIntents(resolvedMoves);
            }
        }
        // Iterate through all the traps
        for (Block[] blocksX : board) {
            for (Block blockXY : blocksX) {
                List<MoveIntent> moves;

                // TODO: implementation of ActionReports for use in a GameMoveHistory
                // Preferably altering the behaviour Methods to return (or get as parameters)
                // the list of ActionReports and MoveIntents

                moves = blockXY.OnExpressConveyorStage(this.programStep);
                moves = resolveMoveIntentConflicts(moves);
                executeMoveIntents(moves);

                moves = blockXY.OnConveyorStage(this.programStep);
                moves = resolveMoveIntentConflicts(moves);
                executeMoveIntents(moves);

                moves = blockXY.OnPusherStage(this.programStep);
                moves = resolveMoveIntentConflicts(moves);
                executeMoveIntents(moves);

                moves = blockXY.OnRotatorStage(this.programStep);
                moves = resolveMoveIntentConflicts(moves);
                executeMoveIntents(moves);

                moves = blockXY.OnPresserStage(this.programStep);
                moves = resolveMoveIntentConflicts(moves);
                executeMoveIntents(moves);

                moves = blockXY.OnLaserStage(this.programStep);
                moves = resolveMoveIntentConflicts(moves);
                executeMoveIntents(moves);

                moves = blockXY.OnCheckPointStage(this.programStep);
                moves = resolveMoveIntentConflicts(moves);
                executeMoveIntents(moves);
            }
        }

        // Send back a collective result of the whole GameRound
    }

    private void turn(Robot robot, Direction directionCard) {
        int rotation;
        switch (directionCard) {
            case Left:
                rotation = 3;
                break;
            case Right:
                rotation = 1;
                break;
            default:
                rotation = 0;
                break;
        }
        robot.setDirection(CardinalDirection.values()[(robot.getDirection().ordinal() + rotation) % 4]);
    }

    private List<List<MoveIntent>> resolveCard(Card card, int robotID) {
        List<List<MoveIntent>> moves = new ArrayList<>();
        // TODO: handle rotations
        if (card.getDirectionCard() != null) {
            turn(robots[robotID], card.getDirectionCard());
        }
        if (card.getUTurn()) {
            uTurn(robots[robotID]);
        }
        for (int i = 0;
             i < card.getMoves() /*TODO: modify card.move() to return the number of moves*/;
             i++) {
            List<MoveIntent> subMoveList = new ArrayList<>();
            subMoveList.add(new MoveIntent(robotID, robots[robotID].getDirection()));
            moves.add(subMoveList);
        }
        return moves;
    }

    private void uTurn(Robot robot) {
        turn(robot, Direction.Left);
        turn(robot, Direction.Left);
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
            //TODO: Log Error instead of throwing it
//            throw new IllegalArgumentException(
//                    "The list of moves should not be null.  (But can contain zero elements.)");
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

    /**
     * @author Finn
     */
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
