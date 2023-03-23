package de.uol.swp.client.lobby;

import de.uol.swp.client.lobby.game.presenter.GamePresenter;
import de.uol.swp.client.lobby.lobby.presenter.LobbyPresenter;
import javafx.scene.Parent;

public class Game {

    private Parent lobbyParent;
    private Parent gameParent;


    private LobbyPresenter lobbyPresenter;
    private GamePresenter gamePresenter;


    public Game(LobbyPresenter lobbyPresenter, Parent lobbyParent) {
        this.lobbyPresenter = lobbyPresenter;
        this.lobbyParent = lobbyParent;
    }

    public void setGameView(GamePresenter gamePresenter, Parent gameParent) {
        this.gamePresenter = gamePresenter;
        this.gameParent = gameParent;
    }

    // -----------------------------------------------------
    // parents
    // -----------------------------------------------------


    public Parent getLobbyParent() {
        return lobbyParent;
    }

    public Parent getGameParent() {
        return gameParent;
    }

    // -----------------------------------------------------
    // presenter
    // -----------------------------------------------------

    public GamePresenter getGamePresenter() {
        return gamePresenter;
    }

    public LobbyPresenter getLobbyPresenter() {
        return lobbyPresenter;
    }




}
