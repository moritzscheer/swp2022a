package de.uol.swp.client;

import de.uol.swp.client.lobby.presenter.LobbyPresenter;

/**
 * Factory for use of injecting the ClientConnection via giuce
 *
 * @author Marco Grawunder
 * @see de.uol.swp.client.di.ClientModule
 * @since 2019-09-19
 */
public interface LobbyPresenterFactory {

    /**
     * Creates an instance of the class ClientConnection
     *
     * @return The ClientConnection object the application is using
     * @see de.uol.swp.client.ClientConnection
     * @since 2019-09-19
     */
    LobbyPresenter create();
}
