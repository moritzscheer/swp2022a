package de.uol.swp.client.tab;


import com.google.common.eventbus.Subscribe;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.SceneManager;
import de.uol.swp.client.lobby.event.ShowJoinOrCreateViewEvent;
import de.uol.swp.client.main.event.ShowMainMenuViewEvent;
import de.uol.swp.client.tab.event.ShowNewNodeEvent;
import de.uol.swp.client.tab.event.ShowNewTabEvent;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class TabPresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/TabView.fxml";
    static final Logger LOG = LogManager.getLogger(SceneManager.class);

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab mainTab;
    private Map<Integer, Tab> lobbiesTabs = new HashMap<>();

    // -----------------------------------------------------
    // Node methods
    // -----------------------------------------------------

    @Subscribe
    public void onShowNewNodeEvent(ShowNewNodeEvent event) {
        setNode(event.getTab(), event.getParent());
    }

    private void setNode(int tab, Parent parent){
        Platform.runLater(() -> {
            if(tab == 0) {
                mainTab.setContent(parent);
            } else {
                //todo change to GameView
            }
        });
    }

    // -----------------------------------------------------
    // Tab methods
    // -----------------------------------------------------

    @Subscribe
    public void onShowNewTabEvent(ShowNewTabEvent event) {
        addTab(event.getLobbyID(), event.getLobbyName(), event.isMultiplayer(), event.getParent());
    }



    private void addTab(Integer lobbyID, String lobbyName, Boolean multiplayer, Parent parent){
        Tab tab = new Tab(lobbyName);

        Platform.runLater(() -> {
            try {
                tab.setContent(parent);
                tab.setOnClosed(new EventHandler<Event>() {
                    @Override
                    public void handle(Event event) {
                        if(multiplayer) {
                            eventBus.post(new ShowJoinOrCreateViewEvent());
                        } else {
                            eventBus.post(new ShowMainMenuViewEvent());
                        }
                    }
                });
                tabPane.getTabs().add(tab);
                lobbiesTabs.put(lobbyID, tab);
                tabPane.getSelectionModel().select(tab);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }


}
