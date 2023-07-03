package de.uol.swp.client;

import de.uol.swp.client.lobbyGame.lobby.presenter.LobbyPresenter;

/**
 * Factory for use of injecting the SceneManager via guice
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.di.ClientModule
 * @since 2022-12-27
 */
public interface LobbyPresenterFactory {

    /**
     * Creates an instance of the SceneManager
     *
     * @author Moritz Scheer
     * @return The SceneManger used inside the client
     * @see de.uol.swp.client.SceneManager
     * @since 2022-12-27
     */
    LobbyPresenter create();
}
