package de.uol.swp.client.lobby.Presenter;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import de.uol.swp.client.AbstractPresenter;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.event.ShowCreateLobbyViewEvent;
import de.uol.swp.client.lobby.event.FindCreateCanceledEvent;
import de.uol.swp.client.main.MainMenuPresenter;
import de.uol.swp.client.user.ClientUserService;
import de.uol.swp.common.lobby.message.LobbyCreatedMessage;
import de.uol.swp.common.user.User;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Manages the registration window
 *
 * @author Marco Grawunder
 * @see de.uol.swp.client.AbstractPresenter
 * @since 2019-08-29
 *
 */
public class FindCreatePresenter extends AbstractPresenter {


    public static final String FXML = "/fxml/FindCreateView.fxml";

    private static final Logger LOG = LogManager.getLogger(MainMenuPresenter.class);

    private User loggedInUser;

    private String lobbyName;

    @Inject
    private LobbyService lobbyService;

    @FXML
    private ListView<String> usersView;

    @FXML
    private ListView<String> lobbyView;

    //private static final RegistrationCanceledEvent registrationCanceledEvent = new RegistrationCanceledEvent();
    private static final FindCreateCanceledEvent findCreateCanceledEvent = new FindCreateCanceledEvent();

    public FindCreatePresenter() {
    }

    /**
     * Constructor
     *
     * @param eventBus The EventBus set in ClientModule
     * @param userService The injected ClientUserService
     * @see de.uol.swp.client.di.ClientModule
     * @since 2019-09-18
     */
    @Inject
    public FindCreatePresenter(EventBus eventBus, ClientUserService userService) {
        setEventBus(eventBus);
    }

    @Subscribe
    public void onLobbyCreatedMessage(LobbyCreatedMessage message) {
        this.loggedInUser = message.getUser();
        this.lobbyName = message.getName();
    }

    /**
     * Method called when the cancel button is pressed
     *
     * This Method is called when the cancel button is pressed. It posts an instance
     * of the RegistrationCanceledEvent to the EventBus the SceneManager is subscribed
     * to.
     *
     * @param event The ActionEvent generated by pressing the register button
     * @see de.uol.swp.client.register.event.RegistrationCanceledEvent
     * @see de.uol.swp.client.SceneManager
     * @since 2019-09-02
     */
    @FXML
    void onCancelButtonPressed(ActionEvent event) {
        eventBus.post(findCreateCanceledEvent);
    }

    @FXML
    void onCreateLobbyButtonPressed(ActionEvent actionEvent) {
        eventBus.post(new ShowCreateLobbyViewEvent());
    }
}