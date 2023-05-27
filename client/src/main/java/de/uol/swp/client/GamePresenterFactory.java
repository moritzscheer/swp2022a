package de.uol.swp.client;

import de.uol.swp.client.lobby.game.presenter.GamePresenter;

public interface GamePresenterFactory {

    GamePresenter create();
}
