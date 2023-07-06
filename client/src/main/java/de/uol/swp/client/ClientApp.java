package de.uol.swp.client;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Guice;
import com.google.inject.Injector;

import de.uol.swp.client.auth.events.ShowLoginViewEvent;
import de.uol.swp.client.di.ClientModule;
import de.uol.swp.client.lobbyGame.game.GameService;
import de.uol.swp.client.lobbyGame.lobby.LobbyService;
import de.uol.swp.client.tab.TabPresenter;
import de.uol.swp.client.user.ClientUserService;
import de.uol.swp.common.Configuration;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.exception.DropUserExceptionMessage;
import de.uol.swp.common.user.exception.RegistrationExceptionMessage;
import de.uol.swp.common.user.exception.ServerNotRespondingExceptionMessage;
import de.uol.swp.common.user.exception.UpdateUserExceptionMessage;
import de.uol.swp.common.user.message.ReturnToMainMenuMessage;
import de.uol.swp.common.user.message.UserLoggedOutMessage;
import de.uol.swp.common.user.response.LoginSuccessfulResponse;
import de.uol.swp.common.user.response.RegistrationSuccessfulResponse;
import de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse;
import de.uol.swp.common.user.response.UserDroppedSuccessfulResponse;

import io.netty.channel.Channel;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * The application class of the client
 *
 * <p>This class handles the startup of the application, as well as, incoming login and registration
 * responses and error messages
 *
 * @author Marco Grawunder
 * @see de.uol.swp.client.ConnectionListener
 * @see javafx.application.Application
 * @since 2017-03-17
 */
public class ClientApp extends Application implements ConnectionListener {

    private static final Logger LOG = LogManager.getLogger(ClientApp.class);

    private String host;
    private int port;

    private ClientUserService userService;

    private LobbyService lobbyService;

    private User user;

    private ClientConnection clientConnection;

    private EventBus eventBus;

    private SceneManager sceneManager;

    private TabPresenter tabPresenter;

    private GameService gameService;

    // -----------------------------------------------------
    // Java FX Methods
    // ----------------------------------------------------

    @Override
    public void init() {
        Parameters p = getParameters();
        List<String> args = p.getRaw();

        if (args.size() != 2) {
            host = "localhost";
            port = Configuration.getDefaultPort();
            LOG.info("Usage: {} host port", ClientConnection.class.getSimpleName());
            LOG.info("Using default port {} {}", port, host);
        } else {
            host = args.get(0);
            port = Integer.parseInt(args.get(1));
        }

        // do not establish connection here
        // if connection is established in this stage, no GUI is shown and
        // exceptions are only visible in console!
    }

    @Override
    public void start(Stage primaryStage) {

        // Client app is created by java, so injection must
        // be handled here manually
        Injector injector = Guice.createInjector(new ClientModule());

        // get user service from guice, is needed for logout
        this.userService = injector.getInstance(ClientUserService.class);

        // get lobby service from guice
        this.lobbyService = injector.getInstance(LobbyService.class);

        // get tabPresenter from guice
        this.tabPresenter = injector.getInstance(TabPresenter.class);

        // get gameService from guice
        this.gameService = injector.getInstance(GameService.class);

        // get event bus from guice
        eventBus = injector.getInstance(EventBus.class);

        // Register this class for de.uol.swp.client.events (e.g. for exceptions)
        eventBus.register(this);

        // Client app is created by java, so injection must
        // be handled here manually
        SceneManagerFactory sceneManagerFactory = injector.getInstance(SceneManagerFactory.class);
        this.sceneManager = sceneManagerFactory.create(primaryStage);

        ClientConnectionFactory connectionFactory =
                injector.getInstance(ClientConnectionFactory.class);
        clientConnection = connectionFactory.create(host, port);
        clientConnection.addConnectionListener(this);
        // JavaFX Thread should not be blocked to long!
        Thread t =
                new Thread(
                        () -> {
                            try {
                                clientConnection.start();
                            } catch (InterruptedException e) {
                                exceptionOccurred(e.getMessage());
                                Thread.currentThread().interrupt();
                            }
                        });
        t.setDaemon(true);
        t.start();
    }

    @Override
    public void connectionEstablished(Channel ch) {
        sceneManager.showTabScreen();
    }

    @Override
    public void stop() throws InterruptedException {
        if (userService != null && user != null) {
            userService.logout(user);
            user = null;
        }
        eventBus.unregister(this);
        // Important: Close connection so connection thread can terminate
        // else client application will not stop
        LOG.trace("Trying to shutting down client ...");
        if (clientConnection != null) {
            clientConnection.stopTimer();
            clientConnection.close();
        }
        LOG.info("ClientConnection shutdown");
    }

    /**
     * Handles successful login
     *
     * <p>If an LoginSuccessfulResponse object is detected on the EventBus this method is called. It
     * tells the SceneManager to show the main menu and sets this clients user to the user found in
     * the object. If the loglevel is set to DEBUG or higher "user logged in successfully " and the
     * username of the logged-in user are written to the log.
     *
     * @param message The LoginSuccessfulResponse object detected on the EventBus
     * @see SceneManager
     * @see LoginSuccessfulResponse
     * @since 2017-03-17
     */
    @Subscribe
    public void onLoginSuccessfulResponse(LoginSuccessfulResponse message) {
        LOG.debug("user logged in successfully {}", message.getUser().getUsername());
        this.user = message.getUser();
        sceneManager.showMainScreen();
    }

    /**
     * Handles unsuccessful registrations
     *
     * <p>If an RegistrationExceptionMessage object is detected on the EventBus this method is
     * called. It tells the SceneManager to show the sever error alert. If the loglevel is set to
     * Error or higher "Registration error " and the error message are written to the log.
     *
     * @param message The RegistrationExceptionMessage object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @see de.uol.swp.common.user.exception.RegistrationExceptionMessage
     * @since 2019-09-02
     */
    @Subscribe
    public void onRegistrationExceptionMessage(RegistrationExceptionMessage message) {
        sceneManager.showServerError(String.format("Registration error %s", message));
        LOG.error("Registration error {}", message);
    }

    /**
     * Handles successful registrations
     *
     * <p>If an RegistrationSuccessfulResponse object is detected on the EventBus this method is
     * called. It tells the SceneManager to show the login window. If the loglevel is set to INFO or
     * higher "Registration Successful." is written to the log.
     *
     * @param message The RegistrationSuccessfulResponse object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @see de.uol.swp.common.user.response.RegistrationSuccessfulResponse
     * @since 2019-09-02
     */
    @Subscribe
    public void onRegistrationSuccessfulMessage(RegistrationSuccessfulResponse message) {
        LOG.info("Registration successful.");
        sceneManager.showLoginScreen();
    }

    /**
     * Handles Logout
     *
     * <p>If an UserLoggedOutMessage object is UserLoggedOutMessage detected on the EventBus this
     * method is called. It tells the SceneManager to show the login window. If the loglevel is set
     * to INFO or higher "User {username} logged out." is written to the log.
     *
     * @param message The UserLoggedOutMessage object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @see de.uol.swp.common.user.message.UserLoggedOutMessage
     * @since 2022-11-08
     */
    @Subscribe
    void onUserLoggedOutMessage(UserLoggedOutMessage message) {
        LOG.info("User {} logged out.", message.getUsername());
        Platform.runLater(
                () -> {
                    if (user != null) {
                        if (message.getUsername().equals(user.getUsername())) {
                            if (tabPresenter.infoLabel2IsVisible()) {
                                eventBus.post(new CloseClientEvent());
                            } else {
                                eventBus.post(new ShowLoginViewEvent());
                            }
                        }
                    }
                });
    }

    /**
     * Handles unsuccessful User-Drops
     *
     * <p>If an DropUserExceptionMessage object is detected on the EventBus this method is called.
     * It tells the SceneManager to show the sever error alert. If the loglevel is set to Error or
     * higher "Drop User error " and the error message are written to the log.
     *
     * @param message The DropUserExceptionMessage object detected on the EventBus
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.client.SceneManager
     * @see de.uol.swp.common.user.exception.DropUserExceptionMessage
     * @since 2022-12-08
     */
    @Subscribe
    public void onDropUserExceptionMessage(DropUserExceptionMessage message) {
        sceneManager.showServerError(String.format("Drop user error %s", message));
        LOG.error("Drop user error {}", message);
    }

    /**
     * Handles Server not responding
     *
     * <p>If an ServerNotRespondingExceptionMessage object is detected on the EventBus this method
     * is called. It tells the SceneManager to show the sever error alert. If the loglevel is set to
     * Error or higher "Server not responding " and the error message are written to the log.
     *
     * @param message The ServerNotRespondingExceptionMessage object detected on the EventBus
     * @author Ole Zimmermann
     * @see de.uol.swp.client.SceneManager
     * @see de.uol.swp.common.user.exception.ServerNotRespondingExceptionMessage
     * @since 2022-12-08
     */
    @Subscribe
    public void onServerNotRespondingExceptionMessage(ServerNotRespondingExceptionMessage message) {
        sceneManager.showServerError(
                String.format(
                        "Server seems to have stopped responding: \n %s", message.toString()));
        LOG.error("Server not responding {}", message);
    }

    /**
     * Handles successful User-Drops
     *
     * <p>If an UserDroppedSuccessfulResponse object is detected on the EventBus this method is
     * called. It tells the SceneManager to show the login window. If the loglevel is set to INFO or
     * higher "User {response} dropped." is written to the log.
     *
     * @param response The UserDroppedSuccessfulResponse object detected on the EventBus
     * @see de.uol.swp.client.SceneManager
     * @see de.uol.swp.common.user.response.UserDroppedSuccessfulResponse
     * @since 2022-11-08
     */
    @Subscribe
    void onUserDroppedSuccessfulResponse(UserDroppedSuccessfulResponse response) {
        LOG.info("User {} has been dropped.", response.getUsername());
        sceneManager.showLoginScreen();
    }

    /**
     * Handles the switch from account view to main-menu
     *
     * <p>If an ReturnToMainMenuRequest object is detected on the EventBus this method is called. It
     * tells the SceneManager to show the main-menu window.
     *
     * @param message The ReturnToMainMenuRequest object detected on the EventBus
     * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.client.SceneManager
     * @see de.uol.swp.common.user.message.ReturnToMainMenuMessage
     * @since 2022-12-02
     */
    @Subscribe
    public void onReturnToMainMenuMessage(ReturnToMainMenuMessage message) {
        LOG.debug("user  {}", message.getLoggedInUser().getUsername());
        this.user = message.getLoggedInUser();
        sceneManager.showMainScreen();
    }

    /**
     * Handles unsuccessful User-Updates
     *
     * <p>If an UpdateUserExceptionMessage object is detected on the EventBus this method is called.
     * It tells the SceneManager to show the sever error alert. If the loglevel is set to Error or
     * higher "Update user error " and the error message are written to the log.
     *
     * @param message The UpdateUserExceptionMessage object detected on the EventBus
     * @author Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.client.SceneManager
     * @see de.uol.swp.common.user.exception.UpdateUserExceptionMessage
     * @since 2022-12-08
     */
    @Subscribe
    public void onUpdateUserExceptionMessage(UpdateUserExceptionMessage message) {
        sceneManager.showServerError(String.format("Update user error %s", message));
        LOG.error("Update user error {}", message);
    }

    /**
     * Handles successful User-Updates
     *
     * <p>If an UpdatedUserSuccessfulResponse object is detected on the EventBus this method is
     * called. It tells the SceneManager to show the main-menu window.
     *
     * @param message The UpdatedUserSuccessfulResponse object detected on the EventBus
     * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.client.SceneManager
     * @see de.uol.swp.common.user.response.UpdatedUserSuccessfulResponse
     * @since 2022-12-02
     */
    @Subscribe
    public void onUpdatedUserSuccessfulResponse(UpdatedUserSuccessfulResponse message) {
        LOG.debug("user  {}", message.getUpdatedUser().getUsername());
        this.user = message.getUpdatedUser();
        sceneManager.showMainScreen();
    }

    /**
     * Handles errors produced by the EventBus
     *
     * <p>If an DeadEvent object is detected on the EventBus, this method is called. It writes
     * "DeadEvent detected " and the error message of the detected DeadEvent object to the log, if
     * the loglevel is set to ERROR or higher.
     *
     * @param deadEvent The DeadEvent object found on the EventBus
     * @since 2019-08-07
     */
    @Subscribe
    private void onDeadEvent(DeadEvent deadEvent) {
        LOG.error("DeadEvent detected {}", deadEvent);
    }

    @Override
    public void exceptionOccurred(String e) {
        sceneManager.showServerError(e);
    }

    // -----------------------------------------------------
    // JavFX Help method
    // -----------------------------------------------------

    /**
     * Default startup method for javafx applications
     *
     * @param args Any arguments given when starting the application
     * @since 2017-03-17
     */
    public static void main(String[] args) {
        launch(args);
    }
}
