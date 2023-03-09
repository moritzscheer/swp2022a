package de.uol.swp.client.lobby;

import de.uol.swp.client.lobby.cards.presenter.CardsPresenter;
import de.uol.swp.client.lobby.game.presenter.GamePresenter;
import de.uol.swp.client.lobby.lobby.presenter.LobbyPresenter;
import javafx.scene.Parent;

public class Game {

    private String status;
    private LobbyPresenter lobbyPresenter;
    private Parent lobbyParent;

    private CardsPresenter cardsPresenter;
    private Parent cardsParent;

    private GamePresenter gamePresenter;
    private Parent gameParent;


    public Game(LobbyPresenter lobbyPresenter, Parent lobbyParent) {
        this.lobbyPresenter = lobbyPresenter;
        this.lobbyParent = lobbyParent;
        status = "lobby";
    }

    public String getStatus() {
        return status;
    }

    // -----------------------------------------------------
    // lobby
    // -----------------------------------------------------

    public LobbyPresenter getLobbyPresenter() {
        return lobbyPresenter;
    }

    public Parent getLobbyParent() {
        return lobbyParent;
    }

    // -----------------------------------------------------
    // cards
    // -----------------------------------------------------

    public CardsPresenter getCardsPresenter() {
        return cardsPresenter;
    }

    public Parent getCardsParent() {
        return cardsParent;
    }

    public void setCardsView(CardsPresenter cardsPresenter, Parent cardsParent) {
        this.cardsPresenter = cardsPresenter;
        this.cardsParent = cardsParent;
        status = "cards";
    }

    // -----------------------------------------------------
    // game
    // -----------------------------------------------------

    public GamePresenter getGamePresenter() {
        return gamePresenter;
    }

    public Parent getGameParent() {
        return gameParent;
    }

    public void setGameView(GamePresenter gamePresenter, Parent gameParent) {
        this.gamePresenter = gamePresenter;
        this.gameParent = gameParent;
        status = "game";
    }


}
