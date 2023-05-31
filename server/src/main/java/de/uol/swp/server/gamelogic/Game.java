package de.uol.swp.server.gamelogic;

import com.google.common.primitives.Ints;
import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.CardDTO;
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

import static de.uol.swp.server.utils.ConvertToDTOUtils.*;
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
    private final Position[] checkpointsList;
    private final Position dockingStartPosition;
    private final List<Robot> robots = new ArrayList<>();
    private final int nRobots;
    private int programStep; // program steps from 0 to 4
    private final Timer timer = new Timer();
    private int readyRegister; // count how many are ready
    private final List<AbstractPlayer> players = new ArrayList<>();
    private final Card[][] playedCards;

    private int[] cardsIDs = IntStream.range(1, 85).toArray(); // From 1 to 84
    List<Integer> cardsIDsList = Arrays.stream(cardsIDs).boxed().collect(Collectors.toList());
    private Map<Integer, Card> cardIdCardMap = new HashMap<>();

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
        this.programStep = 0;
        this.readyRegister = 0;

        // there must be as many docking as users
        //assert dockingBays.length == users.size();
        this.dockingStartPosition = checkpointsList[0];

        // create players and robots
        int i=0; // start robots id in 0
        for(User user: users) {
            Player newPlayer = new Player(convertUserToUserDTO(user), this.dockingStartPosition, i);
            this.players.add(newPlayer);
            this.robots.add(newPlayer.getRobot());
            i++;
        }

        this.nRobots = robots.size();
        this.playedCards = new Card[this.nRobots][5];
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
                        // save references to these Card objetcs
                        cardIdCardMap.put(cards[i].getId(), cards[i]);
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
     * When a player has chosen its cards, he will press "register" button this function will call
     * the player function that will register his cards Once all players have chosen, the calcGame
     * will be called
     *
     * @author Maria
     * @see de.uol.swp.server.gamelogic.Player
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-04-25
     */
    public boolean register(UserDTO loggedInUser, List<CardDTO> playerCards)
            throws InterruptedException {
        // TODO
        // check when all players are ready to register the next cards
        this.readyRegister += 1;
        AbstractPlayer playerIsReady = getPlayerByUserDTO(loggedInUser);
        Card[] chosenCards = new Card[5];
        int i = 0;
        for(CardDTO cardDTO: playerCards){
            chosenCards[i] = cardIdCardMap.get(cardDTO.getID());
            i++;
        }

        playerIsReady.chooseCardsOrder(chosenCards); // set cards of this player

        if (this.readyRegister == this.nRobots - 1) {
            startTimer();
        } else if (this.readyRegister == this.nRobots) {
            this.programStep = 0; // start in the first (0) program step, until 4
            for (int playerIterator = 0; playerIterator < players.size(); playerIterator++) {
                this.playedCards[playerIterator] = players.get(playerIterator).getChosenCards();
            }
            this.readyRegister = 0;
            return true; // return true when all players have played
        }
        return false;
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
    public Map<UserDTO, CardDTO> revealProgramCards() {
        Map<UserDTO, CardDTO> userDTOCardDTOMap = new HashMap<>();
        LOG.debug("REVEALING PROGRAM CARDS STEP: "+ this.programStep);
        for (int playerIterator = 0; playerIterator < players.size(); playerIterator++) {
            userDTOCardDTOMap.put(
                    ((Player)this.players.get(playerIterator)).getUser(),
                    // program steps starts in 1 and this array in 0
                    convertCardToCardDTO(this.playedCards[playerIterator][this.programStep])
            );
        }
        return userDTOCardDTOMap;
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
    public void roundIsOver() throws InterruptedException {

        // round is over
        this.programStep = 0;
        this.notDistributedCards = true; // set to distribute for next round
        // recreate all possible cards
        this.cardsIDs = IntStream.range(1, 85).toArray(); // From 1 to 84
        this.cardsIDsList = Arrays.stream(cardsIDs).boxed().collect(Collectors.toList());
    }

    public void startGame(){
        this.board = MapBuilderTESTMAP.getMap("server/src/main/resources/maps/tempMap.map");
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
    public void calcGameRoundCards(){
        LOG.debug("Calculating game cards for round " + this.programStep);
        // Iterate through the 5 cards
        if (this.playedCards[0].length != 5) {
            // TODO: Log Error regarding card count
        }
        // TODO: row is Player, column is card
        // idea: you can iterate over the players with:
        // this.playedCards[playerIterator][this.programStep]
        // programStep changes in goToNextRound(cards)

        // Iterate through the X card of all Players and resolve them
        LOG.debug("1Current Position of "+((Player)this.players.get(0)).getUser().getUsername());
        LOG.debug("     Position x = {} y = {}", this.robots.get(0).getPosition().x, this.robots.get(0).getPosition().y);
        for (int playerIterator = 0; playerIterator < this.playedCards.length; playerIterator++) {
            if(!this.robots.get(playerIterator).isAlive())
                continue; // if not alive, go on
            List<List<MoveIntent>> moves;
            moves = resolveCard(this.playedCards[playerIterator][this.programStep], playerIterator);
            for (List<MoveIntent> move : moves) {
                List<MoveIntent> resolvedMoves = resolveMoveIntentConflicts(move);
                executeMoveIntents(resolvedMoves);
            }
        }

        checkRobotFellFromBoard();
    }

    public void calcGameRoundBoard(){
        LOG.debug("Calculating game board for round " + this.programStep);
        // Iterate through the 5 cards
        if (this.playedCards[0].length != 5) {
            // TODO: Log Error regarding card count
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

        checkRobotFellFromBoard();
    }

    public void calcGameRound() {
//        LOG.debug("Calculating game for round " + this.programStep);
//        // Iterate through the 5 cards
//        if (this.playedCards[0].length != 5) {
//            // TODO: Log Error regarding card count
//        }
//        // TODO: row is Player, column is card
//        // idea: you can iterate over the players with:
//        // this.playedCards[playerIterator][this.programStep]
//        // programStep changes in goToNextRound(cards)
//
//        // Iterate through the X card of all Players and resolve them
//        LOG.debug("1Current Position of "+((Player)this.players.get(0)).getUser().getUsername());
//        LOG.debug("     Position x = {} y = {}", this.robots.get(0).getPosition().x, this.robots.get(0).getPosition().y);
//        for (int playerIterator = 0; playerIterator < this.playedCards.length; playerIterator++) {
//            List<List<MoveIntent>> moves;
//            moves = resolveCard(this.playedCards[playerIterator][this.programStep], playerIterator);
//            for (List<MoveIntent> move : moves) {
//                List<MoveIntent> resolvedMoves = resolveMoveIntentConflicts(move);
//                executeMoveIntents(resolvedMoves);
//            }
//        }
//        LOG.debug("2Current Position of "+((Player)this.players.get(0)).getUser().getUsername());
//        LOG.debug("     Position x = {} y = {}", this.robots.get(0).getPosition().x, this.robots.get(0).getPosition().y);
//
//        // Iterate through all the traps
//        for (Block[] blocksX : board) {
//            for (Block blockXY : blocksX) {
//                List<MoveIntent> moves;
//
//                // TODO: implementation of ActionReports for use in a GameMoveHistory
//                // Preferably altering the behaviour Methods to return (or get as parameters)
//                // the list of ActionReports and MoveIntents
//
//                moves = blockXY.OnExpressConveyorStage(this.programStep);
//                moves = resolveMoveIntentConflicts(moves);
//                executeMoveIntents(moves);
//
//                moves = blockXY.OnConveyorStage(this.programStep);
//                moves = resolveMoveIntentConflicts(moves);
//                executeMoveIntents(moves);
//
//                moves = blockXY.OnPusherStage(this.programStep);
//                moves = resolveMoveIntentConflicts(moves);
//                executeMoveIntents(moves);
//
//                moves = blockXY.OnRotatorStage(this.programStep);
//                moves = resolveMoveIntentConflicts(moves);
//                executeMoveIntents(moves);
//
//                moves = blockXY.OnPresserStage(this.programStep);
//                moves = resolveMoveIntentConflicts(moves);
//                executeMoveIntents(moves);
//
//                moves = blockXY.OnLaserStage(this.programStep);
//                moves = resolveMoveIntentConflicts(moves);
//                executeMoveIntents(moves);
//
//                moves = blockXY.OnCheckPointStage(this.programStep);
//                moves = resolveMoveIntentConflicts(moves);
//                executeMoveIntents(moves);
//            }
//        }
//        LOG.debug("3Current Position of "+((Player)this.players.get(0)).getUser().getUsername());
//        LOG.debug("     Position x = {} y = {}", this.robots.get(0).getPosition().x, this.robots.get(0).getPosition().y);


        // Send back a collective result of the whole GameRound
    }


    //////////////////////////////
    // SOLVING MOVE INTENTS
    /////////////////////////////

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
                if(!this.robots.get(move.robotID).isAlive())
                    continue; // if not alive, go on
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
        try {
            return board[currentTile.x][currentTile.y].getObstruction(moveDir)
                    || board[destinationTile.x][destinationTile.y].getObstruction(
                    CardinalDirection.values()[moveDir.ordinal() + 2]);
        } catch (ArrayIndexOutOfBoundsException e){
            // testing for out of bounds
            return false;
        }

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
     * set Robot is dead when fell off the grid
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-05-31
     */
    private void checkRobotFellFromBoard(){
        LOG.debug("set not alive");
        for(Robot robot: this.robots){
            Position position = robot.getPosition();
            if(position.x < 0 || position.y < 0 || position.x >= board.length || position.y >= board[0].length){
                robot.setAlive(false);
            }
        }
    }

    //////////////////////////////
    // GETTERS // SETTERS
    /////////////////////////////
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

    /**
     * Getter for the checkPointsList
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour
     * @since 2023-05-19
     */
    public Position[] getCheckpointsList() {
        return this.checkpointsList;
    }

    /**
     * Getter for the Start Position = First CheckPoint
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour
     * @since 2023-05-19
     */
    public Position getDockingStartPosition() {
        return this.dockingStartPosition;
    }

    public int getProgramStep(){
        return this.programStep;
    }

    public void increaseProgramStep(){
        this.programStep++;
    }
}
