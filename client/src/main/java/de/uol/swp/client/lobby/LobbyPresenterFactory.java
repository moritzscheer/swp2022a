package de.uol.swp.client.lobby;

import de.uol.swp.client.lobby.presenter.LobbyPresenter;
import de.uol.swp.common.lobby.dto.LobbyDTO;

public class LobbyPresenterFactory {

    public static LobbyPresenter create(LobbyDTO lobby) {
        return new LobbyPresenter(lobby);
    }
}
