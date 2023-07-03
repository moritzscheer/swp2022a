package de.uol.swp.server.gamelogic;

import static de.uol.swp.server.utils.ConvertToDTOUtils.*;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import de.uol.swp.common.chat.message.TextHistoryMessage;
import de.uol.swp.common.game.dto.BlockDTO;
import de.uol.swp.common.game.dto.CardDTO;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.game.dto.PlayerDTO;
import de.uol.swp.common.game.message.*;
import de.uol.swp.common.lobby.message.AbstractLobbyMessage;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.server.gamelogic.cards.Card;
import de.uol.swp.server.gamelogic.moves.GameMovement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javatuples.Pair;

import java.util.*;

/**
 * Manages creation, deletion and storing of games
 *
 * @author Maria Andrade
 * @see de.uol.swp.common.game.Game
 * @see de.uol.swp.common.game.dto.GameDTO
 * @since 2023-06-26
 */
@Singleton
public class GameManagement {
    private static final Logger LOG = LogManager.getLogger(GameManagement.class);
    private final Map<Integer, Game> games = new HashMap<>();
    private final Map<Integer, GameDTO> gamesDTO = new HashMap<>();

    @Inject
    public GameManagement() {}

    /**
     * Handles the StartGameRequest
     *
     * <p>If a StartGameRequest is detected on the EventBus, this method is called. It creates a
     * Game object linked to the lobbyID, which will be used to retrieve information about the game
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.request.StartGameRequest
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @since 2023-02-28
     */
    public GameDTO createNewGame(
            int lobbyID, String mapName, int numberBots, int checkpointCount, Set<User> users) {
        LOG.debug("createNewGame");

        // create and save Game Object
        games.put(
                lobbyID, // Version -1 if it should be random generated
                new Game(lobbyID, users, mapName, numberBots, checkpointCount, -1));

        // Create DTOs objects
        List<PlayerDTO> players = convertPlayerListToPlayerDTOList(games.get(lobbyID).getPlayers());
        BlockDTO[][] boardDTO = convertBoardToBoardDTO(games.get(lobbyID).getBoard());
        GameDTO gameDTO = new GameDTO(players, boardDTO);

        gamesDTO.put(lobbyID, gameDTO); // save reference to the GameDTO

        LOG.debug("Number of players including Bots: " + players.size());
        return gameDTO;
    }

    /**
     * Searches for the game with the requested gameID
     *
     * @param gameID Integer containing the gameID of the game to search for
     * @return either empty Optional or Optional containing the game
     * @author Maria Andrade
     * @since 2023-05-06
     */
    public Optional<Game> getGame(Integer gameID) {
        for (Map.Entry<Integer, Game> entry : games.entrySet()) {
            if (entry.getKey().equals(gameID)) {
                Game game = games.get(entry.getKey());
                return Optional.of(game);
            }
        }
        return Optional.empty();
    }

    /**
     * Handles GetProgramCardsRequest found on the EventBus
     *
     * <p>If a GetProgramCardsRequest is detected on the EventBus, this method is called. It posts a
     * GetProgramCardsRequest to all the users in the lobby, containing the
     *
     * @param lobbyID lobbyID
     * @author Moritz Scheer, Maria Eduarda Costa Leite Andrade, WKempel
     * @since 2023-02-28
     */
    public boolean getProgramCards(int lobbyID) {
        LOG.debug("onGetProgramCardsRequest");
        Optional<Game> game = getGame(lobbyID);
        boolean callBot = false;
        if (game.isPresent()) {
            // only distribute once, then we get all cards for all players
            callBot = game.get().distributeProgramCards();
        }
        return callBot;
    }

    /**
     * Print Card Infos
     *
     * @param lobbyID lobbyID
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-02-28
     */
    public void printReceivedCards(int lobbyID, UserDTO user) {
        LOG.debug("Server, cards received by user {}", user.getUsername());
        for (Card card : games.get(lobbyID).getPlayerByUserDTO(user).getReceivedCards()) {
            LOG.debug("   id={} priority={}", card.getId(), card.getPriority());
        }
    }

    /**
     * Return an Array of cards distributed to a player
     *
     * @param lobbyID lobbyID
     * @param user UserDTO
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-02-28
     */
    public List<CardDTO> getPlayerReceivedCards(int lobbyID, UserDTO user) {
        return convertCardsToCardsDTO(games.get(lobbyID).getPlayerByUserDTO(user).getReceivedCards());
    }

    /**
     * Return the amount of damage tokens of a robot
     *
     * @param lobbyID lobbyID
     * @param user UserDTO
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-02-28
     */
    public int getPlayerDamageTokens(int lobbyID, UserDTO user) {
        return games.get(lobbyID).getPlayerByUserDTO(user).getRobot().getDamageToken();
    }

    /**
     * Select the cards from the bots after they were distributed
     *
     * @param lobbyID lobbyID
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-02-28
     */
    public Pair<Boolean, List<AbstractLobbyMessage>> selectCardBot(int lobbyID)
            throws InterruptedException {
        Boolean allChosen = games.get(lobbyID).registerCardsFromBot();
        List<AbstractLobbyMessage> msgs = new ArrayList<>();
        for (AbstractPlayer botPlayer : games.get(lobbyID).getPlayers()) {
            if (botPlayer instanceof BotPlayer) {
                PlayerIsReadyMessage msg = new PlayerIsReadyMessage(botPlayer.getUser(), lobbyID);
                msgs.add(msg);
            }
        }

        if (allChosen) {
            LOG.debug("All players have chosen cards");
            manageGameUpdate(lobbyID);
        }

        return new Pair<>(allChosen, msgs);
    }

    /**
     * Handles all rounds, call calcGame() for every round and send messages to update the view
     * using help of a scheduler to give some delay to display
     *
     * @param lobbyID lobbyID to which the game belongs
     * @author Finn Oldeboershuis, Maria Andrade
     * @since 2023-06-13
     */
    public List<Pair<Integer, AbstractLobbyMessage>> manageGameUpdate(int lobbyID) {
        int secondsToWait = 1;
        AbstractLobbyMessage msg = null;
        Game game = games.get(lobbyID);

        List<Pair<Integer, AbstractLobbyMessage>> secondsToMessage = new ArrayList<>();
        secondsToMessage.add(
                new Pair<>(
                        secondsToWait,
                        new TextHistoryMessage(
                                lobbyID,
                                "======= Round: "
                                        + games.get(lobbyID).getRoundNumber()
                                        + " ======= \n")));

        for (int i = 0; i < 5; i++) {
            game.calcAllGameRound();
            List<GameMovement> gameMovements = game.getGameMovements();
            String programStep = String.valueOf(i);

            secondsToMessage.add(
                    new Pair<>(
                            secondsToWait,
                            new TextHistoryMessage(
                                    lobbyID, "==== Program Step: " + programStep + " ==== \n")));

            Map<UserDTO, CardDTO> userDTOCardDTOMap = game.revealProgramCards();

            secondsToMessage.add(
                    new Pair<>(
                            secondsToWait,
                            new ShowAllPlayersCardsMessage(userDTOCardDTOMap, lobbyID)));
            for (GameMovement gameMovement : gameMovements) {
                List<PlayerDTO> moves = gameMovement.getRobotsPositionsInOneMove();

                // just to speed up when there are no moves
                if (gameMovement.isSomeoneMoved() || gameMovement.isCardMove()) secondsToWait += 1;

                secondsToMessage.add(
                        new Pair<>(secondsToWait, new ShowBoardMovingMessage(lobbyID, moves)));
                secondsToMessage.add(
                        new Pair<>(
                                secondsToWait,
                                new TextHistoryMessage(lobbyID, gameMovement.getMoveMessage())));
            }
            msg = isGameOver(lobbyID, game);
            if (!Objects.equals(msg, null)) {
                secondsToMessage.add(new Pair<>(secondsToWait + 1, msg));
                break;
            }

            game.increaseProgramStep();
        }
        secondsToWait += 1;
        // else: if game was over, message was sent already
        if (Objects.equals(msg, null)) {
            UserDTO winner = game.roundIsOver(); // reset variables
            if (Objects.equals(winner, null))
                msg = new RoundIsOverMessage(lobbyID, game.getRespawnRobots());
            else msg = new GameOverMessage(lobbyID, winner);
            secondsToMessage.add(new Pair<>(secondsToWait, msg));
        }
        return secondsToMessage;
    }

    /**
     * Check if a Player achieved the last checkpoint
     *
     * @param game reference to which game
     * @param lobbyID lobbyID to which the game belongs
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.common.game.request.SubmitCardsRequest
     * @since 2023-06-05
     */
    private AbstractLobbyMessage isGameOver(int lobbyID, Game game) {
        for (AbstractPlayer player : game.getPlayers()) {
            if (player.getRobot().getLastCheckPoint() == game.getLastCheckPoint()) {
                return new GameOverMessage(lobbyID, player.getUser());
            }
        }
        UserDTO survivor = new UserDTO("Nobody", "", "");
        int countSurvivors = 0;
        for (AbstractPlayer player : game.getPlayers()) {
            if (!player.getRobot().isDeadForever()) {
                countSurvivors++;
                survivor = player.getUser();
            }
        }
        if (countSurvivors <= 1) {
            // gameover
            return new GameOverMessage(lobbyID, survivor);
        }
        return null;
    }
}
