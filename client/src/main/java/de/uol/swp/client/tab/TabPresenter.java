package de.uol.swp.client.tab;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.tab.event.*;
import de.uol.swp.common.lobby.exception.LobbyLeftExceptionResponse;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
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
     * method is called. It calls the showNode method to switch the content of the tab with the tabID
     * to the parent given to it.
     *
     * @param event The ShowNodeEvent object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onShowNodeEvent(ShowNodeEvent event) {
        showNode(event.getTabID(), event.getParent());
    }

    /**
     * Handles CreateLobbyTab events
     *
     * If an CreateLobbyTabEvent object is detected on the EventBus this
     * method is called. It calls the createTab method to create a tab with the given lobbyID, lobbyName and parent.
     *
     * @param event The CreateLobbyTabEvent object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onCreateLobbyTabEvent(CreateLobbyTabEvent event) {
        createTab(event.getLobby().getName(), event.getLobby().getLobbyID(), event.getParent());
    }

    /**
     * Handles DeleteLobbyTab events
     *
     * If an DeleteLobbyTabEvent object is detected on the EventBus this
     * method is called. It calls the deleteTab method to delete a tab with the given lobbyID.
     *
     * @param event The DeleteLobbyTabEvent object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onDeleteLobbyTabEvent(DeleteLobbyTabEvent event) {
        deleteTab(event.getLobbyID());
    }

    /**
     * Handles LobbyLeaveExceptionResponse messages
     *
     * If an LobbyLeaveExceptionResponse object is detected on the EventBus this
     * method is called.
     *
     * @param message The LobbyLeaveExceptionResponse object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @author Moritz Scheer
     * @since 2023-01-04
     */
    @Subscribe
    public void onLobbyLeaveExceptionResponse(LobbyLeftExceptionResponse message) {
        //todo not existing lobby
    }

    // -----------------------------------------------------
    // helper methods and public methods
    // -----------------------------------------------------

    /**
     * helper method to show a Node
     *
     * This method sets the content of the tab with the tabID to the given parent given as a parameter. If the infoBox
     * is visible, it is set to invisible.
     *
     * @param tabID The Integer containing the lobbyID
     * @param parent Parent containing the content of the fxml file
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    private void showNode(Integer tabID, Parent parent) {
        Platform.runLater(() -> {
            if(infoBox.isVisible()) {
                updateInfoBox();
            }
            tabPane.getTabs().get(tabID).setContent(parent);
        });
    }

    /**
     * helper method to create a tab
     *
     * This method creates a tab with the lobbyName as a tab name and sets the content of the tab to the
     * parent parameter given to it. Then it opens the helper method setupTab to setup important settings and adds the
     * tab to the paneTab. Also, the tab is then selected.
     *
     * @param lobbyName String containing the name of the lobby
     * @param lobbyID The Integer containing the lobbyID
     * @param parent Parent containing the content of the fxml file
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    private void createTab(String lobbyName, Integer lobbyID, Parent parent) {
        Tab tab = new Tab(lobbyName);

        Platform.runLater(() -> {
            try {
                tab.setContent(parent);

                setupTab(tab, lobbyID);

                tabPane.getTabs().add(tab);
                tabPane.getSelectionModel().select(tab);
            } catch(Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * helper method to delete a tab
     *
     * This method removes the tab which has the same ID as the lobbyID given to it.
     *
     * @param lobbyID The Integer containing the lobbyID
     * @author Moritz Scheer
     * @since 2023-01-05
     */
    private void deleteTab(Integer lobbyID) {
        Platform.runLater(() -> {
            tabPane.getTabs().removeIf(tab -> tab.getId() != null && tab.getId().equals(lobbyID.toString()));
            tabPane.getSelectionModel().select(0);
        });
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
        } else {
            infoLabel2.setVisible(true);
        }
    }

    // -----------------------------------------------------
    // InfoBox methods
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
            if(infoLabel1.isVisible()) {
                // if the label "Are you sure you want to log-out?" is visible
                if(tabPane.getTabs().size() > 1) {
                    for(Tab tabs : tabPane.getTabs()) {
                        if(tabs.getId() != null) {
                            lobbyService.leaveLobby(tabs.getText(), (UserDTO) loggedInUser, Integer.valueOf(tabs.getId()), !tab.getText().equals("Singleplayer"));
                        }
                    }
                }
                updateInfoBox();
                userService.logout(loggedInUser);
            } else if (infoLabel2.isVisible()) {
                // if the label "re you sure you want to leave the Lobby?" is visible
                lobbyService.leaveLobby(tab.getText(), (UserDTO) loggedInUser, Integer.valueOf(tab.getId()), true);
                updateInfoBox();

                tabPane.getTabs().remove(tab);
                tabPane.getSelectionModel().select(0);
            }
        });
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
