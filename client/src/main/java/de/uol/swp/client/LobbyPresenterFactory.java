package de.uol.swp.client;

import de.uol.swp.client.lobby.presenter.LobbyPresenter;

public interface LobbyPresenterFactory {

    LobbyPresenter create(Integer lobbyID);
}
