package de.uol.swp.client.di;

import com.google.common.eventbus.EventBus;
import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import de.uol.swp.client.*;
import de.uol.swp.client.lobby.LobbyManagement;
import de.uol.swp.client.lobby.game.Tile;
import de.uol.swp.client.tab.TabPresenter;
import de.uol.swp.client.user.ClientUserService;
import de.uol.swp.client.user.UserService;

import javafx.fxml.FXMLLoader;

/**
 * Module that provides classes needed by the client.
 *
 * @author Marco Grawunder
 * @since 2019-09-18
 */
@SuppressWarnings("UnstableApiUsage")
public class ClientModule extends AbstractModule {
    final EventBus eventBus = new EventBus();
    final TabPresenter tabPresenter = new TabPresenter();
    final LobbyManagement lobbyManagement = new LobbyManagement();

    @Override
    protected void configure() {
        install(
                new FactoryModuleBuilder()
                        .implement(SceneManager.class, SceneManager.class)
                        .build(SceneManagerFactory.class));
        install(
                new FactoryModuleBuilder()
                        .implement(ClientConnection.class, ClientConnection.class)
                        .build(ClientConnectionFactory.class));
        install(new FactoryModuleBuilder().build(LobbyPresenterFactory.class));
        install(new FactoryModuleBuilder().build(CardsPresenterFactory.class));
        install(new FactoryModuleBuilder().build(GamePresenterFactory.class));

        bind(FXMLLoader.class).toProvider(FXMLLoaderProvider.class);
        bind(LobbyManagement.class).toInstance(lobbyManagement);
        bind(TabPresenter.class).toInstance(tabPresenter);
        bind(EventBus.class).toInstance(eventBus);
        bind(ClientUserService.class).to(UserService.class);
    }
}
