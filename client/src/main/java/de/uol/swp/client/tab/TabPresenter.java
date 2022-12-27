package de.uol.swp.client.tab;


import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.SceneManager;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowJoinOrCreateViewEvent;
import de.uol.swp.client.main.event.ShowMainMenuViewEvent;
import de.uol.swp.client.tab.event.CreateNewLobbyTabEvent;
import de.uol.swp.client.tab.event.ShowNewNodeEvent;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class TabPresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/TabView.fxml";
    static final Logger LOG = LogManager.getLogger(SceneManager.class);

    private User loggedInUser;
    @Inject
    private LobbyService lobbyService;
    @FXML
    private TabPane tabPane;
    private final Map<Integer, Tab> lobbiesTabs = new HashMap<>();

    @Subscribe
    public void onLoginSuccessfulResponse(LoginSuccessfulResponse message) {
        this.loggedInUser = message.getUser();
    }

    // -----------------------------------------------------
    // Node methods
    // -----------------------------------------------------

    @Subscribe
    public void onShowNewNodeEvent(ShowNewNodeEvent event) {
        Platform.runLater(() -> tabPane.getTabs().get(event.getTab()).setContent(event.getParent()));
    }

    // -----------------------------------------------------
    // create lobby tab
    // -----------------------------------------------------

    @Subscribe
    public void onCreateNewLobbyTabEvent(CreateNewLobbyTabEvent event) {
        Tab tab = new Tab(event.getLobby().getName());

        Platform.runLater(() -> {
            try {
                tab.setContent(event.getParent());
                tab.setOnClosed(event2 -> {
                    if(event.getLobby().isMultiplayer()) {
                        eventBus.post(new ShowJoinOrCreateViewEvent());
                        lobbyService.leaveLobby(event.getLobby().getName(), (UserDTO) loggedInUser, event.getLobby().getLobbyID(), true);
                    } else {
                        eventBus.post(new ShowMainMenuViewEvent());
                    }
                });
                tabPane.getTabs().add(tab);
                lobbiesTabs.put(event.getLobby().getLobbyID(), tab);
                tabPane.getSelectionModel().select(tab);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }
}
