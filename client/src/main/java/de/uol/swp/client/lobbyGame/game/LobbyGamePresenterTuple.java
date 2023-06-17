package de.uol.swp.client.lobbyGame.game;

import de.uol.swp.client.lobbyGame.game.presenter.GamePresenter;
import de.uol.swp.client.lobbyGame.lobby.presenter.LobbyPresenter;

import javafx.scene.Parent;

/**
 * Class that save up the parent and presenter Objects
 *
 * @author Moritz Scheer
 * @since 2023-03-09
 */
public class LobbyGamePresenterTuple {

    private Parent lobbyParent;
    private Parent gameParent;
    private final LobbyPresenter lobbyPresenter;
    private GamePresenter gamePresenter;

    /**
     * Constructor to save the lobbyPresenter and lobbyParent in the given attributes *
     *
     * @param lobbyPresenter the Presenter of the lobby view
     * @param lobbyParent the Parent Object of the lobby view
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public LobbyGamePresenterTuple(LobbyPresenter lobbyPresenter, Parent lobbyParent) {
        this.lobbyPresenter = lobbyPresenter;
        this.lobbyParent = lobbyParent;
    }

    /**
     * Method to save the gamePresenter and gameParent in the given attributes *
     *
     * @param gamePresenter the Presenter of the game view
     * @param gameParent the Parent Object of the game view
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public void setGameView(GamePresenter gamePresenter, Parent gameParent) {
        this.gamePresenter = gamePresenter;
        this.gameParent = gameParent;
    }

    // -----------------------------------------------------
    // parents
    // -----------------------------------------------------

    /**
     * Getter for the lobbyParent variable
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public Parent getLobbyParent() {
        return lobbyParent;
    }

    /**
     * Getter for the gameParent variable
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public Parent getGameParent() {
        return gameParent;
    }

    // -----------------------------------------------------
    // presenter
    // -----------------------------------------------------

    /**
     * Getter for the gamePresenter variable
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public GamePresenter getGamePresenter() {
        return gamePresenter;
    }

    /**
     * Getter for the lobbyPresenter variable
     *
     * @author Moritz Scheer
     * @since 2023-03-09
     */
    public LobbyPresenter getLobbyPresenter() {
        return lobbyPresenter;
    }
}
