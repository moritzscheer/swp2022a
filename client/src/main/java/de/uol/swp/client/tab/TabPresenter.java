package de.uol.swp.client.tab;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.tab.event.*;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;

public class TabPresenter extends AbstractPresenter {

    public static final String FXML = "/fxml/TabView.fxml";

    private User loggedInUser;

    @Inject
    private LobbyService lobbyService;

    @FXML
    private TabPane tabPane;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    @FXML
    private Label infoLabel1;
    @FXML
    private Label infoLabel2;
    @FXML
    private Pane infoBox;

    // -----------------------------------------------------
    // subscribe methods
    // -----------------------------------------------------

    /**
     * Handles successful login
     *
     * If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser
     * of this client is set to the one in the message received.
     *
     * @param message the LoginSuccessfulResponse object seen on the EventBus
     * @see de.uol.swp.common.user.response.LoginSuccessfulResponse
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onLoginSuccessfulResponse(LoginSuccessfulResponse message) {
        this.loggedInUser = message.getUser();
    }

    /**
     * Handles ShowNode events
     *
     * If an ShowNodeEvent object is detected on the EventBus this
     * method is called. If the loglevel is set to Error or higher "Lobby create error " and the
     * error message are written to the log.
     *
     * @param event The ShowNodeEvent object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onShowNodeEvent(ShowNodeEvent event) {
        Platform.runLater(() -> {
            if(infoBox.isVisible()) {
                updateInfoBox();
            }
            tabPane.getTabs().get(event.getTab()).setContent(event.getParent());
        });
    }

    /**
     * Handles CreateLobbyTab events
     *
     * If an CreateLobbyTabEvent object is detected on the EventBus this
     * method is called.
     *
     * @param event The CreateLobbyTabEvent object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onCreateLobbyTabEvent(CreateLobbyTabEvent event) {
        Tab tab = new Tab(event.getLobby().getName());

        Platform.runLater(() -> {
            try {
                tab.setContent(event.getParent());

                setupTab(tab, event.getLobby().getLobbyID());

                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Handles DeleteLobbyTab events
     *
     * If an DeleteLobbyTabEvent object is detected on the EventBus this
     * method is called.
     *
     * @param event The DeleteLobbyTabEvent object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onDeleteLobbyTabEvent(DeleteLobbyTabEvent event) {
        Platform.runLater(() -> {
            tabPane.getTabs().removeIf(tab -> tab.getId() != null && tab.getId().equals(event.getLobbyID().toString()));
            tabPane.getSelectionModel().select(0);
        });
    }

    // -----------------------------------------------------
    // public methods and helper methods
    // -----------------------------------------------------

    /**
     * method for the visibility of the infoBox
     *
     * If this method is called, it is possible to make the elements of a scene visible or invisible.
     *
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public void updateInfoBox() {
        if(!infoBox.isVisible()){
            infoBox.setVisible(true);
            yesButton.setVisible(true);
            noButton.setVisible(true);
            setInfoLabel();
        } else {
            infoBox.setVisible(false);
            yesButton.setVisible(false);
            noButton.setVisible(false);
            infoLabel1.setVisible(false);
            infoLabel2.setVisible(false);
        }
    }

    /**
     * helper method to set up a tab
     *
     * This method sets the id to the tab id and defines EventHandler for different events
     *
     * @param tab The Tab containing the tab data
     * @param lobbyID The Integer containing the lobbyID
     * @author Moritz Scheer
     * @since 2022-12-28
     */
    private void setupTab(Tab tab, Integer lobbyID) {
        tab.setId(lobbyID.toString());

        tab.setOnCloseRequest(closeEvent -> {
            closeEvent.consume();
            eventBus.post(new ChangeElementEvent());
            updateInfoBox();
        });

        tab.setOnSelectionChanged(changeEvent -> {
            changeEvent.consume();
            if(infoBox.isVisible()) { updateInfoBox(); }
        });
    }

    /**
     * helper method for the visibility of the infoLabels
     *
     * If this method is called, it sets the infoLabels according to the currently selected tab to visible
     *
     * @author Moritz Scheer
     * @since 2022-12-28
     */
    private void setInfoLabel() {
        if(tabPane.getSelectionModel().getSelectedItem().getId() == null) {
            infoLabel1.setVisible(true);
            infoLabel1.setVisible(true);
        } else {
            infoLabel2.setVisible(true);
            infoLabel2.setVisible(true);
        }
    }

    // -----------------------------------------------------
    // ActionEvents
    // -----------------------------------------------------

    /**
     * Method called when the yes button in the infoBox is pressed
     *
     * This Method is called when the yes button is pressed.
     *
     * @author Daniel Merzo & Moritz Scheer
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @since 2022-12-27
     */
    @FXML
    private void onYesButtonPressed(ActionEvent actionEvent){
        Tab tab = tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex());

        Platform.runLater(() -> {
            if(tab.getId() == null && tabPane.getTabs().size() > 1) {
                // if the currently selected tab is the main tab and there is more than one tab open
                for(Tab tabs : tabPane.getTabs()) {
                    if(tabs.getId() != null) {
                        lobbyService.leaveLobby(tabs.getText(), (UserDTO) loggedInUser, Integer.valueOf(tabs.getId()), !tab.getText().equals("Singleplayer"));
                    }
                }
                userService.logout(loggedInUser);
            } else if (tab.getId() != null) {
                // if the currently selected tab is not the main tab
                tabPane.getTabs().remove(tab);
                tabPane.getSelectionModel().select(0);
                lobbyService.leaveLobby(tab.getText(), (UserDTO) loggedInUser, Integer.valueOf(tab.getId()), true);
            } else {
                // if only the main tab is open
                userService.logout(loggedInUser);
            }
        });
        updateInfoBox();
    }

    /**
     * Method called when the no button in the infoBox is pressed
     *
     * This Method is called when the no button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    @FXML
    private void onNoButtonPressed(ActionEvent actionEvent){
        eventBus.post(new ChangeElementEvent());
        updateInfoBox();
    }

}
