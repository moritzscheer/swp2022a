package de.uol.swp.client.tab;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.tab.event.CreateLobbyTabEvent;
import de.uol.swp.client.tab.event.DeleteLobbyTabEvent;
import de.uol.swp.client.tab.event.ShowNodeEvent;
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
    private Label infoLabel;
    @FXML
    private Pane infoBox;

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
        Platform.runLater(() -> tabPane.getTabs().get(event.getTab()).setContent(event.getParent()));
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

                // set tab settings
                tab.setId(event.getLobby().getLobbyID().toString());
                tab.setOnCloseRequest(closeEvent -> {
                    closeEvent.consume();
                    updateInfoBox();
                });
                tab.setId(event.getLobby().getLobbyID().toString());

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
            tabPane.getTabs().removeIf(tab -> tab.getId().equals(event.getLobbyID().toString()));
            tabPane.getSelectionModel().select(0);
        });
    }

    /**
     * helper method for the visibility of the infoBox
     *
     * If this method is called, it is possible to make the elements of a scene visible or invisible.
     *
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    private void updateInfoBox(){
        if(!infoBox.isVisible()){
            infoBox.setVisible(true);
            yesButton.setVisible(true);
            noButton.setVisible(true);
            infoLabel.setVisible(true);
        } else {
            infoBox.setVisible(false);
            yesButton.setVisible(false);
            noButton.setVisible(false);
            infoLabel.setVisible(false);
        }
    }

    /**
     * Method called when the no button in the infoBox is pressed
     *
     * This Method is called when the no button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @FXML
    private void onNoButtonPressed(ActionEvent actionEvent){
        updateInfoBox();
    }

    /**
     * Method called when the no button in the infoBox is pressed
     *
     * This Method is called when the no button is pressed.
     *
     * @param actionEvent The ActionEvent generated by pressing the cancel button
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @FXML
    private void onYesButtonPressed(ActionEvent actionEvent){
        Platform.runLater(() -> {
            Tab tab = tabPane.getTabs().get(tabPane.getSelectionModel().getSelectedIndex());
            tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
            tabPane.getSelectionModel().select(0);
            lobbyService.leaveLobby(tab.getText(), (UserDTO) loggedInUser, Integer.valueOf(tab.getId()), true);
        });
        updateInfoBox();
    }
}
