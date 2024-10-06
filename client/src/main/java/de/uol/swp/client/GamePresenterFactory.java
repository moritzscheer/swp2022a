package de.uol.swp.client;

import de.uol.swp.client.lobbyGame.game.presenter.GamePresenter;

/**
 * Factory for use of injecting the SceneManager via guice
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.di.ClientModule
 * @since 2023-03-09
 */
public interface GamePresenterFactory {

    /**
     * Creates an instance of the SceneManager
     *
     * @author Moritz Scheer
     * @return The SceneManger used inside the client
     * @see de.uol.swp.client.SceneManager
     * @since 2023-03-09
     */
    GamePresenter create();
}
