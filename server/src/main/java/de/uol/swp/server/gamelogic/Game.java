package de.uol.swp.server.gamelogic;

import com.google.common.primitives.Ints;
import de.uol.swp.common.game.Position;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.cards.Direction;
import de.uol.swp.common.game.enums.CardinalDirection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static de.uol.swp.server.utils.ConvertToDTOUtils.convertUserToUserDTO;
import static de.uol.swp.server.utils.JsonUtils.searchCardInJSON;

/**
 * @author Maria Andrade & Finn Oldeboershuis
 * @see
 * @since 2023
 */
public class Game {
    private static final Logger LOG = LogManager.getLogger(Game.class);

    private final Integer lobbyID;
    private Block[][] board;

    // TODO: Remove dockingBays field
    private Position[] checkpointsList;
    private final List<Robot> robots = new ArrayList<>();
    private final int nRobots;
    private int rowCount;
    private int columnCount;
    private int programStep; // program steps from 1 to 5
    private final Timer timer = new Timer();
    private int readyRegister; // count how many are ready
    private final List<AbstractPlayer> players = new ArrayList<>();
    private final Card[][] playedCards;

    private int[] cardsIDs = IntStream.range(1, 85).toArray(); // From 1 to 84
    List<Integer> cardsIDsList = Arrays.stream(cardsIDs).boxed().collect(Collectors.toList());

    private boolean notDistributedCards = true;
    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.Block
     * @see Position
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 20-02-2023
     */
    public Game(Integer lobbyID, Position[] checkpointsList, Set<User> users) {
        this.lobbyID = lobbyID;
        this.checkpointsList = checkpointsList;
        //this.rowCount = board.length;
        //this.columnCount = board[0].length;
        this.programStep = 0;
        this.readyRegister = 0;

        // there must be as many docking as users
        //assert dockingBays.length == users.size();

        // create players and robots
        for(User user: users) {
            Player newPlayer = new Player(convertUserToUserDTO(user), checkpointsList[0]);
            this.players.add(newPlayer);
            this.robots.add(newPlayer.getRobot());
        }

        this.nRobots = robots.size();
        this.playedCards = new Card[this.nRobots][5];
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
            for (int playerIterator = 0; playerIterator < players.size(); playerIterator++) {
                this.playedCards[playerIterator] = players.get(playerIterator).getChosenCards();
            }
            revealProgramCards();
            goToNextRound();
            this.readyRegister = 0;
        }
    }

    /**
     * Generate random cards for a player (max. 9, min. 5)
     * The cards are generated based on the id, from 1 to 84
     *
     * @author Maria
     * @see de.uol.swp.server.gamelogic.Player
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-04-25
     */
    public void distributeProgramCards() {
        if(notDistributedCards) {
            // there will be many request to get the cards, one from each player
            // therefore the distribution should be done one single time
            notDistributedCards = false;

            Collections.shuffle(cardsIDsList);
            Integer[] intArray = new Integer[84];
            cardsIDsList.toArray(intArray);
            System.out.println(Arrays.toString(intArray));
            int count = 0;

            for (AbstractPlayer player : this.players) {
                LOG.debug("Distributing cards for player {}", ((Player)player).getUser().getUsername());

                int damage = player.getRobot().getDamageToken();

                if (damage < 5) {
                    int[] cardsIDs = Arrays.copyOfRange(Ints.toArray(cardsIDsList), count, count + 9 - damage);
                    Card[] cards = new Card[9 - damage];
                    int i = 0;
                    for (int cardID : cardsIDs) {
                        cards[i] = searchCardInJSON(cardID);
                        i++;
                    }
                    player.receiveCards(cards);
                    count = count + 9 - damage;

                }
                // TODO: lock the registers
                else {
                }
            }
        }
    }

    /**
     * Sends response to client with cards to be displayed
     * One from each Player
     *
     * @author Maria
     * @see de.uol.swp.server.gamelogic.Player
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-04-25
     */
    public Card[] revealProgramCards() {
        // TODO: send message to client

        Card[] cardsInThisProgramStep = new Card[this.nRobots];
        for (int playerIterator = 0; playerIterator < players.size(); playerIterator++) {
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
        this.notDistributedCards = true; // set to distribute for next round
        // recreate all possible cards
        this.cardsIDs = IntStream.range(1, 85).toArray(); // From 1 to 84
        this.cardsIDsList = Arrays.stream(cardsIDs).boxed().collect(Collectors.toList());
    }

    public void startGame(){
        this.board = MapBuilder.getMap("server/src/main/resources/maps/tempMap.map");
        if(board == null){
            //TODO: Log error "Map couldn't be loaded"
            return;
        }
        setRobotsInfoInBehaviours(board, robots);
    }

    private void setRobotsInfoInBehaviours(Block[][] board, List<Robot> robots) {
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board[x].length; y++) {
                board[x][y].setRobotsInfo(robots);
            }
        }
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
        robot.setDirection(
                CardinalDirection.values()[(robot.getDirection().ordinal() + rotation) % 4]);
    }

    private List<List<MoveIntent>> resolveCard(Card card, int robotID) {
        List<List<MoveIntent>> moves = new ArrayList<>();
        // TODO: handle rotations
        if (card.getDirectionCard() != null) {
            turn(robots.get(robotID), card.getDirectionCard());
        }
        if (card.getUTurn()) {
            uTurn(robots.get(robotID));
        }
        for (int i = 0;
                i < card.getMoves() /*TODO: modify card.move() to return the number of moves*/;
                i++) {
            List<MoveIntent> subMoveList = new ArrayList<>();
            subMoveList.add(new MoveIntent(robotID, robots.get(robotID).getDirection()));
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
                robots.get(move.robotID).move(move.direction);
            }
        }
    }

    public List<MoveIntent> resolveMoveIntentConflicts(List<MoveIntent> movesIn) {
        ArrayList<MoveResult> moveList = new ArrayList<>();

        if (movesIn == null) {
            // TODO: Log Error instead of throwing it
            //            throw new IllegalArgumentException(
            //                    "The list of moves should not be null.  (But can contain zero
            // elements.)");
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

            for (int j = 0; j < robots.size(); j++) {
                Robot robot = robots.get(j);
                if (!robot.equals(robots.get(move.robotID))) {
                    if (robot.getPosition() == destinationTile) {
                        boolean alreadyHasMoveIntent = false;
                        for (MoveResult moveResult : moveList) {
                            if (robot.equals(robots.get(moveResult.robotID))) {
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
            Position p = robots.get(robotID).getPosition();
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
            return robots.get(robotID).getPosition();
        }
    }

    /**
     * Getter for lobbyID
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.lobby.dto.LobbyDTO
     * @since 2023-05-08
     */
    public Integer getLobbyID() {
        return lobbyID;
    }
    /**
     * Getter for Board Array
     *
     * @author Jann Erik Bruns, Daniel Merzo
     * @see de.uol.swp.server.gamelogic.AbstractPlayer
     * @since 2023-05-16
     */
    public Block[][] getBoard(){ return board;}
    /**
     * Setter for Board Array
     *
     * @author Jann Erik Bruns, Daniel Merzo
     * @see de.uol.swp.server.gamelogic.AbstractPlayer
     * @since 2023-05-16
     */
    public void setBoard(Block[][] board){ this.board = board;}
    /**
     * Getter for list of Players
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.AbstractPlayer
     * @since 2023-05-13
     */
    public List<AbstractPlayer> getPlayers(){
        return this.players;
    }

    /**
     * Getter a single Player based on User id
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.AbstractPlayer
     * @since 2023-05-18
     */
    public Player getPlayerByUserDTO(UserDTO user){
        for(AbstractPlayer player: players){
            if(player.getClass() == Player.class &&
                    Objects.equals(((Player) player).getUser(), user)){
                return ((Player)player);
            }
        }
        return null;
    }
}
