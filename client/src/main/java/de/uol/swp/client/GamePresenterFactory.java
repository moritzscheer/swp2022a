package de.uol.swp.client;

import de.uol.swp.client.lobbyGame.game.presenter.GamePresenter;

public interface GamePresenterFactory {

    GamePresenter create();
}
