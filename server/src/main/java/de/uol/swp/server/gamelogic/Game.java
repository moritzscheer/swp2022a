package de.uol.swp.server.gamelogic;

import static de.uol.swp.server.utils.ConvertToDTOUtils.*;
import static de.uol.swp.server.utils.JsonUtils.searchCardInJSON;
import static de.uol.swp.server.utils.JsonUtils.searchCardType;

import com.google.common.primitives.Ints;

import de.uol.swp.common.game.Position;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.enums.CardinalDirection;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.cards.Direction;
import de.uol.swp.server.gamelogic.map.MapBuilder;
import de.uol.swp.server.gamelogic.moves.GameMovement;
import de.uol.swp.server.gamelogic.moves.MoveIntent;
import de.uol.swp.server.gamelogic.tiles.*;
import de.uol.swp.server.utils.ConvertToDTOUtils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Maria Andrade & Finn Oldeboershuis
 * @since 2023
 */
public class Game {
    private static final Logger LOG = LogManager.getLogger(Game.class);

    private final Integer lobbyID;
    private Block[][] board;
    private int roundNumber = 1;

    // TODO: Remove dockingBays field
    private final Position startCheckpoint;
    private final int lastCheckPoint;
    private final Position dockingStartPosition;
    private final List<Robot> robots = new ArrayList<>();
    private int nRobots; // we update the number of robots
    private int nRealPlayers;
    private int programStep; // program steps from 0 to 4
    private int readyRegister; // count how many are ready
    private final List<AbstractPlayer> players = new ArrayList<>();
    private final Card[][] playedCards;

    private final String mapName;

    private final int checkpointCount;

    private int[] cardsIDs = IntStream.range(1, 85).toArray(); // From 1 to 84
    List<Integer> cardsIDsList = Arrays.stream(cardsIDs).boxed().collect(Collectors.toList());
    private static final Set<Integer> cardsIdsOnlyTurn =
            new HashSet<>(
                    Arrays.asList(
                            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                            21, 22, 23, 24, 2, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,
                            39, 40, 41, 42));
    private Map<Integer, Card> cardIdCardMap = new HashMap<>();

    private boolean notDistributedCards = true;
    private String fullMapName;
    private List<GameMovement> gameMovements;
    private List<PlayerDTO> respawnRobots;
    private List<PlayerDTO> sendOnlyOneMessageDeadForever = new ArrayList<>();
    private UserDTO wonTheGame = null;

    /**
     * Constructor
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.Block
     * @see Position
     * @see de.uol.swp.server.gamelogic.Robot
     * @since 20-02-2023
     */
    public Game(
            Integer lobbyID,
            Set<User> users,
            String mapName,
            int numberBots,
            int checkpointCount,
            int version) {
        this.lobbyID = lobbyID;
        this.programStep = 0;
        this.readyRegister = 0;
        this.mapName = mapName;
        this.checkpointCount = checkpointCount;

        assert users.size() + numberBots <= 8;

        this.startCheckpoint = loadMapBuilder(version);

        if (this.board == null) {
            // TODO: Log error "Map couldn't be loaded"
            LOG.error("CheckPoints couldn't be loaded. MapName = " + mapName);
        }

        updateBoardInAllBehaviours(); // otherwise laser don't work
        LOG.debug("Checkpoints size: {}", this.checkpointCount);
        LOG.debug("StartPosition x={}, y={}", this.startCheckpoint.x, this.startCheckpoint.y);

        // set info
        setRobotsInfoInBehaviours(board, robots);

        // there must be as many docking as users
        // assert dockingBays.length == users.size();
        this.dockingStartPosition = startCheckpoint;
        this.lastCheckPoint = this.checkpointCount;

        // create players and robots
        int i = 0; // start robots id in 0
        if (!Objects.equals(users, null)) // important to run integration tests
        for (User user : users) {
                Player newPlayer =
                        new Player(convertUserToUserDTO(user), this.dockingStartPosition, i);
                this.players.add(newPlayer);
                this.robots.add(newPlayer.getRobot());
                i++;
            }

        nRealPlayers = users.size();

        // create bots and robots
        for (int j = 0; j < numberBots; j++) {
            BotPlayer newPlayer = new BotPlayer(this.dockingStartPosition, i);
            this.players.add(newPlayer);
            this.robots.add(newPlayer.getRobot());
            i++;
        }

        this.nRobots = robots.size();
        this.playedCards = new Card[this.nRobots][5];
    }

    /**
     * Method to load Map outside constructor
     *
     * @author Jann, Tommy, Maxim, Maria, WKempel
     * @see de.uol.swp.server.gamelogic.map.MapBuilder
     * @since 2023-06-19
     */
    private Position loadMapBuilder(int version) {
        LOG.debug(new File(".").getAbsolutePath());
        // create board
        if (version == -1) {
            Random random = new Random();
            version = random.nextInt(3) + 1;
        }

        fullMapName = this.mapName + "V" + version + "C" + checkpointCount;

        LOG.debug(version);
        LOG.debug(
                "server/src/main/resources/maps/"
                        + this.mapName
                        + "V"
                        + version
                        + "C"
                        + checkpointCount
                        + ".map");
        Pair<Integer, Position> tmp;

        if (this.mapName.contains("Test")) {
            this.board =
                    MapBuilder.getMap("server/src/main/resources/maps/" + this.mapName + ".map");
            tmp = MapBuilder.getMapStringToCheckpointNumberAndFirstPosition(mapName);
        } else {
            String mapPath = "";

            // TODO: this is for testing purposes, because for some reason the tests are run from
            // swp2022/server/ while the server is run from swp2022/
            if (new File(".").getAbsolutePath().endsWith("server\\.")) {
                mapPath =
                        "src/main/resources/maps/"
                                + this.mapName
                                + "V"
                                + version
                                + "C"
                                + checkpointCount
                                + ".map";
            } else {
                mapPath =
                        "server/src/main/resources/maps/"
                                + this.mapName
                                + "V"
                                + version
                                + "C"
                                + checkpointCount
                                + ".map";
            }

            LOG.debug(new File(mapPath).getAbsolutePath());
            this.board = MapBuilder.getMap(mapPath);

            if (board == null) {
                // TODO: Log error "Map couldn't be loaded"
                LOG.debug("Map couldn't be loaded. MapName = " + mapName);
            }

            // save checkPoints
            LOG.debug(version);
            tmp =
                    MapBuilder.getMapStringToCheckpointNumberAndFirstPosition(
                            mapName + "V" + version + "C" + checkpointCount);
        }
        assert tmp.getValue0() == checkpointCount;

        return tmp.getValue1();
    }

    /**
     * Update board in each Behaviour, because some behaviours are dynamically added
     *
     * <p>(In order to laser logic to works)
     *
     * @author Maria
     * @see de.uol.swp.server.gamelogic.Block
     * @since 2023-07-03
     */
    private void updateBoardInAllBehaviours() {
        for (Block[] boardCol : board)
            for (Block block : boardCol)
                for (AbstractTileBehaviour behaviour : block.getBehaviourList())
                    behaviour.setBoard(board);
    }

    /**
     * Generate random cards for a player (max. 9, min. 5) The cards are generated based on the id,
     * from 1 to 84
     *
     * @author Maria
     * @see de.uol.swp.server.gamelogic.Player
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-04-25
     */
    public boolean distributeProgramCards() {
        if (notDistributedCards) {
            // there will be many request to get the cards, one from each player
            // therefore the distribution should be done one single time
            notDistributedCards = false;

            Collections.shuffle(cardsIDsList);
            Integer[] intArray = new Integer[84];
            cardsIDsList.toArray(intArray);
            System.out.println(Arrays.toString(intArray));
            int count = 0;

            for (AbstractPlayer player : this.players) {
                // when robot is powered off, just set empty cards
                if (player.getRobot().isPowerDown() || player.getRobot().isDeadForever()) {
                    continue;
                }
                LOG.debug("Distributing cards for player {}", player.getUser().getUsername());

                int damage = player.getRobot().getDamageToken();

                if (damage < 5) {
                    int[] cardsIDs =
                            Arrays.copyOfRange(
                                    Ints.toArray(cardsIDsList), count, count + 9 - damage);

                    while (cardsIdsOnlyTurn.containsAll(
                            Arrays.stream(cardsIDs).boxed().collect(Collectors.toList()))) {
                        LOG.debug("Ups, all cards are turn type");
                        Collections.shuffle(cardsIDsList);
                        cardsIDs =
                                Arrays.copyOfRange(
                                        Ints.toArray(cardsIDsList), count, count + 9 - damage);

                        // prevent that it runs forever, then redistribute to all players again
                        if (cardsIdsOnlyTurn.containsAll(cardsIDsList)) {
                            LOG.debug("New Distribution of cards to all players ");
                            notDistributedCards = true;
                            distributeProgramCards();
                        }
                    }

                    Card[] cards = new Card[9 - damage];
                    int i = 0;
                    for (int cardID : cardsIDs) {
                        cards[i] = searchCardInJSON(cardID);
                        // save references to these Card objects
                        cardIdCardMap.put(cards[i].getId(), cards[i]);
                        i++;
                    }
                    player.receiveCards(cards);
                    count = count + 9 - damage;

                } else {
                    int[] cardsIDs =
                            Arrays.copyOfRange(Ints.toArray(cardsIDsList), count, count + 5);

                    while (cardsIdsOnlyTurn.containsAll(
                            Arrays.stream(cardsIDs).boxed().collect(Collectors.toList()))) {
                        LOG.debug("Whoops, all cards are turn type");
                        Collections.shuffle(cardsIDsList);
                        cardsIDs = Arrays.copyOfRange(Ints.toArray(cardsIDsList), count, count + 5);

                        // prevent that it runs forever, then redistribute to all players again
                        if (cardsIdsOnlyTurn.containsAll(cardsIDsList)) {
                            LOG.debug("New Distribution of cards to all players");
                            notDistributedCards = true;
                            distributeProgramCards();
                        }
                    }

                    Card[] cards = new Card[5];
                    int i = 0;
                    for (int cardID : cardsIDs) {
                        cards[i] = searchCardInJSON(cardID);
                        // save references to these Card objects
                        assert cards[i] != null;
                        cardIdCardMap.put(cards[i].getId(), cards[i]);
                        i++;
                    }
                    player.receiveCards(cards);
                    count = count + 5;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * The method iterate over all AbstractPlayer if there are any players who are a BotPlayer we're
     * saving the first five cards from the botPlayer received cards in chosenCards. Also, we set
     * botPlayers of ready with the method register.
     *
     * @author WKempel & Maria
     * @return allReady
     * @throws InterruptedException
     */
    public boolean registerCardsFromBot() throws InterruptedException {
        boolean allReady = false;
        for (AbstractPlayer botPlayer : this.players) {
            if (botPlayer instanceof BotPlayer) {
                if(botPlayer.getRobot().isDeadForever()) continue;
                Card[] receivedCards = botPlayer.getReceivedCards();
                botPlayer.chooseCardsOrder(chooseFirstCardMoveBot(receivedCards));
                System.out.println(receivedCards.length); // set cards of this bot
                allReady = register();
            }
        }
        return allReady;
    }

    /**
     * The method makes sure that the first card of the first round is a move
     *
     * @return chosenCards @Author Maria
     * @since 2023-06-23
     */
    public Card[] chooseFirstCardMoveBot(Card[] receivedCards) {
        if (this.roundNumber != 1) {
            return Arrays.copyOfRange(receivedCards, 0, 5);
        }
        Card[] chosenCards = Arrays.copyOfRange(receivedCards, 0, 5);
        LOG.debug("Bot cards:");
        for (int i = 0; i < 5; i++) {
            LOG.debug(chosenCards[i].getBehaviourType());
        }
        if (Objects.equals(chosenCards[0].getBehaviourType(), "1")
                || Objects.equals(chosenCards[0].getBehaviourType(), "3")
                || Objects.equals(chosenCards[0].getBehaviourType(), "4")) {
            int i = 1;
            while (Objects.equals(chosenCards[i].getBehaviourType(), "1")
                    || Objects.equals(chosenCards[i].getBehaviourType(), "3")
                    || Objects.equals(chosenCards[i].getBehaviourType(), "4")) {

                i++;
                if (i == 5) {
                    break;
                }
            }
            if (i == 5) {
                // did not find a Move card on chosen
                for (int j = 5; j < receivedCards.length; j++) {
                    if (!Objects.equals(receivedCards[j].getBehaviourType(), "1")
                            && !Objects.equals(receivedCards[j].getBehaviourType(), "3")
                            && !Objects.equals(receivedCards[j].getBehaviourType(), "4")) {
                        chosenCards[0] = receivedCards[j];
                        break;
                    }
                }
            } else {
                Card tmp = chosenCards[0];
                chosenCards[0] = chosenCards[i];
                chosenCards[i] = tmp;
            }
        }
        LOG.debug("Bot cards AFTER:");
        for (int i = 0; i < 5; i++) {
            LOG.debug(chosenCards[i].getBehaviourType());
        }
        return chosenCards;
    }

    /**
     * When a player has chosen its cards, he will press "register" button this function will call
     * the player function that will register his cards Once all players have chosen, the calcGame
     * will be called
     *
     * @author WKempel & Maria
     * @param loggedInUser
     * @param playerCards
     * @return register
     * @throws InterruptedException
     */
    public boolean registerCardsFromUser(UserDTO loggedInUser, List<CardDTO> playerCards)
            throws InterruptedException {
        AbstractPlayer playerIsReady = getPlayerByUserDTO(loggedInUser);
        Card[] chosenCards = new Card[5];
        int i = 0;
        for (CardDTO cardDTO : playerCards) {
            chosenCards[i] = cardIdCardMap.get(cardDTO.getID());
            i++;
        }

        playerIsReady.chooseCardsOrder(chosenCards); // set cards of this player

        return register();
    }

    public boolean setPowerDown(UserDTO userDTO) throws InterruptedException {
        Player player = getPlayerByUserDTO(userDTO);
        player.getRobot().setPowerDown(true);
        LOG.debug("setPowerDown {}", player.getUser().getUsername());

        // set empty cards
        Card[] cards = new Card[5];
        for (int i = 0; i < 5; i++) {
            cards[i] = new Card(-1);
        }
        player.chooseCardsOrder(cards);

        // this player lose all damage
        player.getRobot().setDamageToken(0);

        boolean allChosen = register();

        // if all real players decided to turn off, we have to
        // start the bots manually, so the game can happen
        if (nRealPlayers == readyRegister) {
            distributeProgramCards();
            return registerCardsFromBot();
        }
        return allChosen;
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
    public boolean register() throws InterruptedException {
        // check when all players are ready to register the next cards
        this.readyRegister += 1;

        if (this.readyRegister == this.nRobots) {
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
     * Sends response to client with cards to be displayed One from each Player
     *
     * @author Maria
     * @see de.uol.swp.server.gamelogic.Player
     * @see de.uol.swp.server.gamelogic.cards.Card
     * @since 2023-04-25
     */
    public Map<UserDTO, CardDTO> revealProgramCards() {
        Map<UserDTO, CardDTO> userDTOCardDTOMap = new HashMap<>();
        LOG.debug("REVEALING PROGRAM CARDS STEP: " + this.programStep);
        for (int playerIterator = 0; playerIterator < players.size(); playerIterator++) {
            userDTOCardDTOMap.put(
                    this.players.get(playerIterator).getUser(),
                    // program steps starts in 1 and this array in 0
                    convertCardToCardDTO(this.playedCards[playerIterator][this.programStep]));
        }
        return userDTOCardDTOMap;
    }

    /**
     * Increase program step to get the new cards from each player Clear gameMovements
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-04-25
     */
    public void increaseProgramStep() {
        this.programStep++;
        this.gameMovements = null;
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
    public UserDTO roundIsOver() {

        // round is over
        this.programStep = 0;
        this.notDistributedCards = true; // set to distribute for next round
        // recreate all possible cards
        this.cardsIDs = IntStream.range(1, 85).toArray(); // From 1 to 84
        this.cardsIDsList = Arrays.stream(cardsIDs).boxed().collect(Collectors.toList());
        this.roundNumber++;
        this.respawnRobots = new ArrayList<>();

        int countSurvivors = 0;
        int countDeadForever = 0;
        UserDTO survivor = null;
        for (AbstractPlayer player : this.players) {
            if (!player.getRobot().isDeadForever()) {
                // only when dead for the round, set in the backup
                boolean addRespawn = false;
                if (player.getRobot().isDeadForTheRound()) {
                    player.getRobot()
                            .setCurrentPosition(player.getRobot().getLastBackupCopyPosition());
                    addRespawn = true;
                }

                player.getRobot().setAlive(true);
                player.getRobot().setDeadForTheRound(false);
                player.getRobot().setPowerDown(false);

                // needs to be done down here after all variables were set
                if (addRespawn) respawnRobots.add(convertPlayerToPlayerDTO(player));

                countSurvivors++;
                survivor = player.getUser();
            } else {
                countDeadForever++;
                // this is case real players died forever and all others turn off
                if (player instanceof Player)
                    nRealPlayers--;
                Card[] deadCards = new Card[5];
                for (int i = 0; i < 5; i++) {
                    deadCards[i] = new Card(-1);
                }
                player.chooseCardsOrder(deadCards);
            }
        }
        this.nRobots =
                players.size() - countDeadForever; // to not wait on the dead ones to play the round

        // test game over
        if (wonTheGame != null) return wonTheGame;
        else if (countSurvivors <= 1) return survivor;

        return null;
    }

    /**
     * @author Maria & Finn
     * @since 2023-05-06
     */
    private void setRobotsInfoInBehaviours(Block[][] board, List<Robot> robots) {
        for (Block[] blocks : board) {
            for (Block block : blocks) {
                block.setRobotsInfo(robots);
            }
        }
    }

    /**
     * check if all robots are dead or turned off to not keep sending messages
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-06-20
     */
    public boolean areAllRobotsAreDead() {
        for (Robot robot : this.robots) {
            if (!robot.isDeadForTheRound()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @author Maria & Finn
     * @since 2023-06-22
     */
    public void calcAllGameRound() {
        gameMovements = new ArrayList<>();
        if (areAllRobotsAreDead() || wonTheGame != null) {
            return;
        }
        gameMovements.add(new GameMovement(getPlayerDTOSForAllPlayers(), null, null, ""));

        calcGameRoundCardsNew();
        calcGameRoundBoardNew();
    }

    /**
     * @author Finn
     * @since 2023-06-05
     */
    public List<PlayerDTO> getPlayerDTOSForAllPlayers() {
        List<PlayerDTO> initialPlayerStates = new ArrayList<>();
        for (AbstractPlayer player : players) {
            initialPlayerStates.add(ConvertToDTOUtils.convertPlayerToPlayerDTO(player));
        }
        return initialPlayerStates;
    }

    /**
     * @author Finn & Maria
     * @since 2023-06-05
     */
    private List<List<PlayerDTO>> calcGameRoundBoardNew() {
        List<MoveIntent> currentMoves;
        List<List<PlayerDTO>> moves = new ArrayList<>();

        currentMoves = onExpressConveyorStage();
        currentMoves = resolveMoveIntentConflicts(currentMoves);
        executeMoveIntents(currentMoves);
        GameMovement oldMove = this.gameMovements.get(this.gameMovements.size() - 1);
        this.gameMovements.add(
                new GameMovement(getPlayerDTOSForAllPlayers(), "ExpressConveyor", oldMove, ""));
        moves.add(getPlayerDTOSForAllPlayers());

        currentMoves = onConveyorStage();
        currentMoves = resolveMoveIntentConflicts(currentMoves);
        executeMoveIntents(currentMoves);
        oldMove = this.gameMovements.get(this.gameMovements.size() - 1);
        this.gameMovements.add(
                new GameMovement(getPlayerDTOSForAllPlayers(), "Conveyor", oldMove, ""));
        moves.add(getPlayerDTOSForAllPlayers());

        currentMoves = onPusherStage();
        currentMoves = resolveMoveIntentConflicts(currentMoves);
        executeMoveIntents(currentMoves);
        oldMove = this.gameMovements.get(this.gameMovements.size() - 1);
        this.gameMovements.add(
                new GameMovement(getPlayerDTOSForAllPlayers(), "Pusher", oldMove, ""));
        moves.add(getPlayerDTOSForAllPlayers());

        currentMoves = onRotatorStage();
        currentMoves = resolveMoveIntentConflicts(currentMoves);
        executeMoveIntents(currentMoves);
        oldMove = this.gameMovements.get(this.gameMovements.size() - 1);
        this.gameMovements.add(new GameMovement(getPlayerDTOSForAllPlayers(), "Gear", oldMove, ""));
        moves.add(getPlayerDTOSForAllPlayers());

        currentMoves = onPresserStage();
        currentMoves = resolveMoveIntentConflicts(currentMoves);
        executeMoveIntents(currentMoves);
        oldMove = this.gameMovements.get(this.gameMovements.size() - 1);
        this.gameMovements.add(
                new GameMovement(getPlayerDTOSForAllPlayers(), "Presser", oldMove, ""));
        moves.add(getPlayerDTOSForAllPlayers());

        currentMoves = OnLaserStage();
        currentMoves = resolveMoveIntentConflicts(currentMoves);
        executeMoveIntents(currentMoves);
        oldMove = this.gameMovements.get(this.gameMovements.size() - 1);
        moves.add(getPlayerDTOSForAllPlayers());

        currentMoves = OnCheckPointStage();
        currentMoves = resolveMoveIntentConflicts(currentMoves);
        executeMoveIntents(currentMoves);
        oldMove = this.gameMovements.get(this.gameMovements.size() - 1);
        this.gameMovements.add(
                new GameMovement(getPlayerDTOSForAllPlayers(), "Laser/CheckPoint", oldMove, ""));
        moves.add(getPlayerDTOSForAllPlayers());

        // must execute this actions at the end
        executeBehavioursInEndDestination();
        return moves;
    }

    /**
     * @author Maria & Finn
     * @since 2023-06-05
     */
    private List<List<PlayerDTO>> calcGameRoundCardsNew() {
        List<List<PlayerDTO>> moves = new ArrayList<>();
        Card[] cardsToPlay = new Card[playedCards.length];

        for (int i = 0; i < cardsToPlay.length; i++) {
            cardsToPlay[i] = playedCards[i][programStep];
        }

        // Get lists of the Priorities to execute cards in the right order
        Integer[] sortedPriorities =
                Arrays.stream(cardsToPlay)
                        .map(Card::getPriority)
                        .sorted(Comparator.reverseOrder())
                        .toArray(Integer[]::new);
        Integer[] priorities =
                Arrays.stream(cardsToPlay).map(Card::getPriority).toArray(Integer[]::new);

        for (int i = 0; i < cardsToPlay.length; i++) {
            if (wonTheGame != null) break;
            // Get index of next card to play
            int indexOfCurrentCard = Arrays.asList(priorities).indexOf(sortedPriorities[i]);
            Card currentCard = cardsToPlay[indexOfCurrentCard];

            // Check if executing robot is alive or powerdown
            if (!robots.get(indexOfCurrentCard).isAlive()
                    || this.robots.get(indexOfCurrentCard).isPowerDown()) {
                continue; // if not alive or powered down, go on
            }

            // Get Move Intents and execute them
            List<List<MoveIntent>> moveIntents = resolveCard(currentCard, indexOfCurrentCard);
            GameMovement oldMove;
            String cardType = searchCardType(currentCard.getId());
            for (List<MoveIntent> move : moveIntents) {
                List<MoveIntent> resolvedMoves = resolveMoveIntentConflicts(move);
                executeMoveIntents(resolvedMoves);
                oldMove = this.gameMovements.get(this.gameMovements.size() - 1);
                this.gameMovements.add(
                        new GameMovement(
                                getPlayerDTOSForAllPlayers(),
                                cardType,
                                oldMove,
                                players.get(indexOfCurrentCard).getUser().getUsername()));
                cardType = null;
            }
            if (moveIntents.size() == 0) {
                oldMove = this.gameMovements.get(this.gameMovements.size() - 1);
                this.gameMovements.add(
                        new GameMovement(
                                getPlayerDTOSForAllPlayers(),
                                cardType,
                                oldMove,
                                players.get(indexOfCurrentCard).getUser().getUsername()));
            }
            System.out.println(
                    this.gameMovements.get(this.gameMovements.size() - 1).getMoveMessage());
            moves.add(getPlayerDTOSForAllPlayers());
        }
        return moves;
    }

    /**
     * @author Maria
     * @since 2023-06-19
     */
    private List<MoveIntent> onExpressConveyorStage() {
        List<MoveIntent> moves = new ArrayList<>();
        for (Block[] boardCol : board) {
            for (Block block : boardCol) {
                List<MoveIntent> blockMoves = block.onExpressConveyorStage(programStep + 1);
                moves.addAll(blockMoves);
            }
        }
        return moves;
    }

    /**
     * @author Maria
     * @since 2023-06-19
     */
    private List<MoveIntent> onConveyorStage() {
        List<MoveIntent> moves = new ArrayList<>();
        for (Block[] boardCol : board) {
            for (Block block : boardCol) {
                List<MoveIntent> blockMoves = block.OnConveyorStage(programStep + 1);
                moves.addAll(blockMoves);
            }
        }
        return moves;
    }

    /**
     * @author Maria
     * @since 2023-06-19
     */
    private List<MoveIntent> onPusherStage() {

        List<MoveIntent> moves = new ArrayList<>();
        for (Block[] boardCol : board) {
            for (Block block : boardCol) {
                List<MoveIntent> blockMoves = block.onPusherStage(programStep + 1);
                moves.addAll(blockMoves);
            }
        }
        return moves;
    }

    /**
     * @author Maria
     * @since 2023-06-19
     */
    private List<MoveIntent> onRotatorStage() {

        List<MoveIntent> moves = new ArrayList<>();
        for (Block[] boardCol : board) {
            for (Block block : boardCol) {
                List<MoveIntent> blockMoves = block.OnRotatorStage(programStep + 1);
                moves.addAll(blockMoves);
            }
        }
        return moves;
    }

    /**
     * @author Maria
     * @since 2023-06-19
     */
    private List<MoveIntent> onPresserStage() {

        List<MoveIntent> moves = new ArrayList<>();
        for (Block[] boardCol : board) {
            for (Block block : boardCol) {
                List<MoveIntent> blockMoves = block.onPressorStage(programStep + 1);
                moves.addAll(blockMoves);
            }
        }
        return moves;
    }

    /**
     * @author Maria
     * @since 2023-06-19
     */
    private List<MoveIntent> OnLaserStage() {

        List<MoveIntent> moves = new ArrayList<>();
        for (Block[] boardCol : board) {
            for (Block block : boardCol) {
                List<MoveIntent> blockMoves = block.onLaserStage(programStep + 1);
                moves.addAll(blockMoves);
            }
        }
        return moves;
    }

    /**
     * @author Maria
     * @since 2023-06-19
     */
    private List<MoveIntent> OnCheckPointStage() {

        List<MoveIntent> moves = new ArrayList<>();
        for (Block[] boardCol : board) {
            for (Block block : boardCol) {
                List<MoveIntent> blockMoves = block.OnCheckPointStage(programStep + 1);
                moves.addAll(blockMoves);
            }
        }
        return moves;
    }

    //////////////////////////////
    // SOLVING MOVE INTENTS
    /////////////////////////////

    /**
     * @author Maria
     * @since 2023-04-18
     */
    private List<List<MoveIntent>> resolveCard(Card card, int robotID) {
        List<List<MoveIntent>> moves = new ArrayList<>();
        // TODO: handle rotations
        if (card.getDirectionCard() != null) {
            turn(robots.get(robotID), card.getDirectionCard());
        }
        if (card.getUTurn()) {
            uTurn(robots.get(robotID));
        }
        if (card.getMoves() == -1) {
            List<MoveIntent> subMoveList = new ArrayList<>();
            CardinalDirection dir = robots.get(robotID).getDirection();
            subMoveList.add(
                    new MoveIntent(robotID, CardinalDirection.values()[(dir.ordinal() + 2) % 4]));
            moves.add(subMoveList);
        }
        for (int i = 0; i < card.getMoves(); i++) {
            List<MoveIntent> subMoveList = new ArrayList<>();
            subMoveList.add(new MoveIntent(robotID, robots.get(robotID).getDirection()));
            moves.add(subMoveList);
        }
        return moves;
    }

    /**
     * @author Finn & Maria
     * @since 2023-04-24
     */
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

    /**
     * @author Finn
     * @since 2023-04-24
     */
    private void uTurn(Robot robot) {
        turn(robot, Direction.Left);
        turn(robot, Direction.Left);
    }

    /**
     * @author Maria
     * @since 2023-03-24
     */
    private void executeMoveIntents(List<MoveIntent> moves) {
        if (wonTheGame != null) return;
        if (moves != null) {
            for (MoveIntent move : moves) {
                if (!this.robots.get(move.robotID).isAlive()) continue; // if not alive, go on
                robots.get(move.robotID).move(move.direction);
                // after robot moved to new block, check for behaviours to be executed
                executeBehavioursBetweenDestination(move.robotID);
                if (wonTheGame != null) break;
            }
        }

        checkRobotFellFromBoard();
    }
    /**
     * Execute behaviour that may occur only by passing through the block and does not require in
     * the block to land i.e.: Checkpoint, save BackupPosition in repair or checkpoint block, fell
     * on a pit
     *
     * <p>P.S.: does not repair damage tokens
     *
     * @author Maria Andrade
     * @see de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour
     * @see de.uol.swp.server.gamelogic.tiles.RepairBehaviour
     * @see de.uol.swp.server.gamelogic.tiles.PitBehaviour
     * @since 2023-06-12
     */
    private void executeBehavioursBetweenDestination(int robotID) {
        // in new position where robot is, check all behaviours
        Position position = robots.get(robotID).getPosition();
        try {
            for (AbstractTileBehaviour behaviour :
                    board[position.x][position.y].getBehaviourList()) {
                if (behaviour instanceof CheckPointBehaviour) {
                    int checkPoint = ((CheckPointBehaviour) behaviour).setCheckPoint(robotID);
                    if (checkPoint == this.lastCheckPoint) {
                        wonTheGame = players.get(robotID).getUser();
                        break;
                    }
                } else if (behaviour instanceof RepairBehaviour) {
                    ((RepairBehaviour) behaviour).setBackupCopy(robotID);
                } else if (behaviour instanceof PitBehaviour) {
                    behaviour.onRobotEntered(robotID);
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            LOG.debug("Robot fell from the board!");
        }
    }

    /**
     * Execute behaviour that may occur only by passing through the block and does not require in
     * the block to land i.e.: discard damageToken (checkpoint/repair), suffer Laser
     *
     * @author Maria Andrade
     * @see de.uol.swp.server.gamelogic.tiles.CheckPointBehaviour
     * @see de.uol.swp.server.gamelogic.tiles.RepairBehaviour
     * @since 2023-06-12
     */
    private void executeBehavioursInEndDestination() {
        // execute board elements functions, other than moves
        if (wonTheGame != null) return;
        try {
            for (Robot robot : robots) {
                if (!robot.isAlive()) continue;
                Position position = robot.getPosition();
                for (AbstractTileBehaviour behaviour :
                        board[position.x][position.y].getBehaviourList()) {
                    if (behaviour instanceof CheckPointBehaviour) {
                        ((CheckPointBehaviour) behaviour).fixDamageToken(robot.getID());
                    } else if (behaviour instanceof RepairBehaviour) {
                        ((RepairBehaviour) behaviour).fixDamageToken(robot.getID());
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            LOG.debug("Robot fell from the board!");
        }
    }

    /**
     * @author Finn
     * @since 2023-03-06
     */
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

    /**
     * @author Finn
     * @since 2023-03-06
     */
    private boolean addPushMoves(ArrayList<MoveResult> moveList, boolean somethingChanged) {
        for (int i = 0; i < moveList.size(); i++) {
            MoveResult move = moveList.get(i);
            Position currentTile = move.getOriginPosition();
            Position destinationTile = move.getTargetPosition();
            CardinalDirection moveDir = move.getDirection();

            for (int j = 0; j < robots.size(); j++) {
                Robot robot = robots.get(j);
                if (!robot.equals(robots.get(move.robotID))) {
                    if (robot.getPosition().equals(destinationTile) && robot.isAlive()) {
                        boolean alreadyHasMoveIntent = false;
                        for (MoveResult moveResult : moveList) {
                            if (robot.equals(robots.get(moveResult.robotID))) {
                                alreadyHasMoveIntent = true;
                                break;
                            }
                        }
                        if (!alreadyHasMoveIntent) {
                            moveList.add(new MoveResult(move, j));
                            somethingChanged = true;
                        }
                    }
                }
            }
        }
        return somethingChanged;
    }

    /**
     * @author Finn
     * @since 2023-03-13
     */
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
                        MoveResult tmp = moveList.get(j);
                        removeMoveResultAndParents(move, moveList);
                        removeMoveResultAndParents(tmp, moveList);
                        i = -1;
                        j = -1;
                        somethingChanged = true;
                        break;
                    }
                }
            }
        }
        return somethingChanged;
    }

    /**
     * @author Finn
     * @since 2023-03-13
     */
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

    /**
     * @author Finn & Maria
     * @since 2023-03-13
     */
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
                                            (moveList.get(j).getDirection().ordinal() + 2) % 4]
                            && destinationTile == moveList.get(j).getOriginPosition()) {
                        MoveResult tmp = moveList.get(j);
                        removeMoveResultAndParents(move, moveList);
                        removeMoveResultAndParents(tmp, moveList);
                        i = -1;
                        j = -1;
                        somethingChanged = true;
                    }
                }
            }
        }
        return somethingChanged;
    }

    /**
     * @author Maria
     * @since 2023-03-28
     */
    private static boolean checkForObstruction(
            Position currentTile,
            Position destinationTile,
            CardinalDirection moveDir,
            Block[][] board) {
        try {
            return board[currentTile.x][currentTile.y].getObstruction(moveDir)
                    || board[destinationTile.x][destinationTile.y].getObstruction(
                            CardinalDirection.values()[(moveDir.ordinal() + 2) % 4]);
        } catch (ArrayIndexOutOfBoundsException e) {
            // testing for out of bounds
            return false;
        }
    }

    /**
     * @author Finn
     * @since 2023-03-13
     */
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

        /**
         * @author Finn
         * @since 2023-03-06
         */
        public MoveResult(MoveIntent intent) {
            super(intent.robotID, intent.direction);
            parentMove = null;
        }

        /**
         * @author Finn
         * @since 2023-03-06
         */
        public MoveResult(MoveResult parentMove, int robotID) {
            super(robotID, parentMove.direction);
            this.parentMove = parentMove;
        }

        /**
         * @author Finn
         * @since 2023-03-06
         */
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

        /**
         * @author Finn
         * @since 2023-03-13
         */
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
    private void checkRobotFellFromBoard() {
        for (Robot robot : this.robots) {
            if (!robot.isAlive()) {
                continue;
            }
            Position position = robot.getPosition();
            if (position.x < 0
                    || position.y < 0
                    || position.x >= board.length
                    || position.y >= board[0].length) {
                robot.setAlive(false);
            }
        }
    }

    //////////////////////////////
    // GETTERS // SETTERS
    /////////////////////////////

    /**
     * @author Maria
     * @since 2023-07-05
     */
    public UserDTO getWonTheGame() {
        return wonTheGame;
    }

    /**
     * @author Maria
     * @since 2023-07-05
     */
    public List<PlayerDTO> getSendOnlyOneMessageDeadForever() {
        return sendOnlyOneMessageDeadForever;
    }

    /**
     * @author Maria
     * @since 2023-07-05
     */
    public void addSendOnlyOneMessageDeadForever(PlayerDTO playerDeadForever) {
        this.sendOnlyOneMessageDeadForever.add(playerDeadForever);
    }

    /**
     * @author WKempel
     * @since 2023-06-02
     */
    public int getRoundNumber() {
        return roundNumber;
    }

    /**
     * @author Maria
     * @since 2023-06-05
     */
    public int getLastCheckPoint() {
        return lastCheckPoint;
    }

    /**
     * @author Maria
     * @since 2023-06-20
     */
    public List<GameMovement> getGameMovements() {
        if (Objects.equals(gameMovements, null)) return new ArrayList<>();
        return gameMovements;
    }

    /**
     * @author Maria
     * @since 2023-06-22
     */
    public List<PlayerDTO> getRespawnRobots() {
        return respawnRobots;
    }

    /**
     * Getter for Board Array
     *
     * @author Jann Erik Bruns, Daniel Merzo
     * @see de.uol.swp.server.gamelogic.AbstractPlayer
     * @since 2023-05-16
     */
    public Block[][] getBoard() {
        return this.board;
    }
    /**
     * Setter for Board Array
     *
     * @author Jann Erik Bruns, Daniel Merzo
     * @see de.uol.swp.server.gamelogic.AbstractPlayer
     * @since 2023-05-16
     */
    public void setBoard(Block[][] board) {
        this.board = board;
    }
    /**
     * Getter for list of Players
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.AbstractPlayer
     * @since 2023-05-13
     */
    public List<AbstractPlayer> getPlayers() {
        return this.players;
    }

    /**
     * Getter a single Player based on User id
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.server.gamelogic.AbstractPlayer
     * @since 2023-05-18
     */
    public Player getPlayerByUserDTO(UserDTO user) {
        for (AbstractPlayer player : players) {
            if (player.getClass() == Player.class && Objects.equals(player.getUser(), user)) {
                return ((Player) player);
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
    public Position getStartCheckpoint() {
        return this.startCheckpoint;
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

    public String getFullMapName() {
        return fullMapName;
    }

    public void setNotDistributedCards(boolean notDistributedCards) {
        this.notDistributedCards = notDistributedCards;
    }

    public boolean isNotDistributedCards() {
        return notDistributedCards;
    }

    public int getProgramStep() {
        return programStep;
    }
}
