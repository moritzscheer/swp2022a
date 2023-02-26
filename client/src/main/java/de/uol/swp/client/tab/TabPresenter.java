package de.uol.swp.client.tab;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;

import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.tab.event.*;
import de.uol.swp.common.lobby.exception.LobbyLeftExceptionResponse;
import de.uol.swp.common.lobby.response.LobbyDroppedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyLeftSuccessfulResponse;
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

    @Inject private LobbyService lobbyService;

    @FXML private TabPane tabPane;
    @FXML private Button yesButton;
    @FXML private Button noButton;
    @FXML private Label infoLabel1; // Are you sure you want to log-out?
    @FXML private Label infoLabel2; // Are you sure you want to exit?
    @FXML private Label infoLabel3; // Are you sure you want to leave the Lobby?
    @FXML private Pane infoBox;

    // -----------------------------------------------------
    // subscribe methods
    // -----------------------------------------------------

    /**
     * Handles successful login
     *
     * <p>If a LoginSuccessfulResponse is posted to the EventBus the loggedInUser of this client is
     * set to the one in the message received.
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
     * <p>If an ShowNodeEvent object is detected on the EventBus this method is called. This method
     * sets the content of the tab with the tabID to the given parent given as a parameter. If the
     * infoBox is visible, it is set to invisible.
     *
     * @param event The ShowNodeEvent object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onShowNodeEvent(ShowNodeEvent event) {
        Platform.runLater(
                () -> {
                    if (infoBox.isVisible()) {
                        updateInfoBox();
                    }
                    tabPane.getTabs().get(event.getTabID()).setContent(event.getParent());
                });
    }

    /**
     * Handles CreateLobbyTab events
     *
     * <p>If an CreateLobbyTabEvent object is detected on the EventBus this method is called. This
     * method creates a tab with the lobbyName as a tab name and sets the content of the tab to the
     * parent parameter given to it. Then it opens the helper method setupTab to Set up important
     * settings and adds the tab to the paneTab. Also, the tab is then selected.
     *
     * @param event The CreateLobbyTabEvent object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onCreateLobbyTabEvent(CreateLobbyTabEvent event) {
        Tab tab = new Tab(event.getLobby().getName());

        Platform.runLater(
                () -> {
                    try {
                        tab.setContent(event.getParent());

                        setupTab(tab, event.getLobby().getLobbyID());

                        tabPane.getTabs().add(tab);
                        tabPane.getSelectionModel().select(tab);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    /**
     * Handles successfully dropped Lobbies
     *
     * <p>If an LobbyDroppedResponse object is detected on the EventBus this method is called. It
     * calls a private method to close a tab.
     *
     * @param message The LobbyDroppedResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onLobbyDroppedSuccessfulResponse(LobbyDroppedSuccessfulResponse message) {
        deleteLobbyTab(message.getLobbyID());
    }

    /**
     * Handles successfully left Lobbies
     *
     * <p>If an LobbyLeaveUserResponse object is detected on the EventBus this method is called. It
     * calls a private method to close a tab.
     *
     * @param message The LobbyLeaveUserResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onLobbyLeaveUserResponse(LobbyLeftSuccessfulResponse message) {
        deleteLobbyTab(message.getLobby().getLobbyID());
    }

    /**
     * Handles LobbyLeaveExceptionResponse messages
     *
     * <p>If an LobbyLeaveExceptionResponse object is detected on the EventBus this method is
     * called.
     *
     * @param message The LobbyLeaveExceptionResponse object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @author Moritz Scheer
     * @since 2023-01-04
     */
    @Subscribe
    public void onLobbyLeaveExceptionResponse(LobbyLeftExceptionResponse message) {
        // todo not existing lobby
    }

    // -----------------------------------------------------
    // helper methods public methods
    // -----------------------------------------------------

    /**
     * helper method to delete a tab
     *
     * <p>This method removes the tab which has the same ID as the lobbyID given to it.
     *
     * @param lobbyID The Integer containing the lobbyID
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    private void deleteLobbyTab(Integer lobbyID) {
        Platform.runLater(
                () -> {
                    tabPane.getTabs()
                            .removeIf(
                                    tab ->
                                            tab.getId() != null
                                                    && tab.getId().equals(lobbyID.toString()));
                    tabPane.getSelectionModel().select(0);
                });
    }

    /**
     * helper method to set up a tab
     *
     * <p>This method sets the id to the tab id and defines EventHandler for different events
     *
     * @param tab The Tab containing the tab data
     * @param lobbyID The Integer containing the lobbyID
     * @author Moritz Scheer
     * @since 2022-12-28
     */
    private void setupTab(Tab tab, Integer lobbyID) {
        tab.setId(lobbyID.toString());

        tab.setOnCloseRequest(
                closeEvent -> {
                    closeEvent.consume();
                    infoLabel3.setVisible(true);
                    updateInfoBox();
                    eventBus.post(new ChangeElementEvent(lobbyID));
                });

        tab.setOnSelectionChanged(
                changeEvent -> {
                    changeEvent.consume();
                    if (infoBox.isVisible()) {
                        updateInfoBox();
                        eventBus.post(new ChangeElementEvent(lobbyID));
                    }
                });
    }

    // -----------------------------------------------------
    // public methods
    // -----------------------------------------------------

    /**
     * method for the visibility of the infoBox
     *
     * <p>If this method is called, it is possible to make the elements of a scene visible or
     * invisible.
     *
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    public void updateInfoBox() {
        if (!infoBox.isVisible()) {
            infoBox.setVisible(true);
            yesButton.setVisible(true);
            noButton.setVisible(true);
        } else {
            infoBox.setVisible(false);
            yesButton.setVisible(false);
            noButton.setVisible(false);
            infoLabel1.setVisible(false);
            infoLabel2.setVisible(false);
            infoLabel3.setVisible(false);
        }
    }

    /**
     * method for the visibility of the infoLabels
     *
     * <p>If this method is called, it sets the infoLabels according to the currently selected tab
     * to visible
     *
     * @author Moritz Scheer
     * @since 2022-12-28
     */
    public void setInfoLabel(Integer infoLabelNumber) {
        if (infoLabelNumber == 1) {
            infoLabel1.setVisible(true);
        } else if (infoLabelNumber == 2) {
            infoLabel2.setVisible(true);
        } else {
            infoLabel3.setVisible(true);
        }
    }

    /**
     * method for checking if an exit request send
     *
     * @author Moritz Scheer
     * @since 2022-12-28
     */
    public boolean onExitRequest() {
        return infoLabel2.isVisible();
    }

    // -----------------------------------------------------
    // InfoBox methods
    // -----------------------------------------------------

    /**
     * Method called when the yes button in the infoBox is pressed
     *
     * <p>This Method is called when the yes button is pressed.
     *
     * @author Daniel Merzo & Moritz Scheer
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @since 2022-12-27
     */
    @FXML
    private void onYesButtonPressed(ActionEvent actionEvent) {
        Tab tab = tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex());

        Platform.runLater(
                () -> {
                    if (infoLabel1.isVisible() || infoLabel2.isVisible()) {
                        if (tabPane.getTabs().size() > 1) {
                            for (Tab tabs : tabPane.getTabs()) {
                                if (tabs.getId() != null) {
                                    lobbyService.leaveLobby(
                                            tabs.getText(),
                                            (UserDTO) loggedInUser,
                                            Integer.valueOf(tabs.getId()),
                                            !tab.getText().equals("Singleplayer"));
                                }
                            }
                        }
                        // updateInfoBox();
                        userService.logout(loggedInUser);
                    } else if (infoLabel3.isVisible()) {
                        lobbyService.leaveLobby(
                                tab.getText(),
                                (UserDTO) loggedInUser,
                                Integer.valueOf(tab.getId()),
                                true);
                        updateInfoBox();

                        tabPane.getTabs().remove(tab);
                        tabPane.getSelectionModel().select(0);
                    }
                });
    }

    /**
     * Method called when the no button in the infoBox is pressed
     *
     * <p>This Method is called when the no button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @author Daniel Merzo
     * @since 2022-12-15
     */
    @FXML
    private void onNoButtonPressed(ActionEvent actionEvent) {
        if (tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex()).getId() != null) {
            eventBus.post(
                    new ChangeElementEvent(
                            Integer.valueOf(
                                    tabPane.getTabs()
                                            .get(tabPane.getSelectionModel().getSelectedIndex())
                                            .getId())));
        }
        updateInfoBox();
    }
}
