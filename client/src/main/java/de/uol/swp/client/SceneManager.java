package de.uol.swp.client;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.assistedinject.Assisted;
import de.uol.swp.client.auth.LoginPresenter;
import de.uol.swp.client.main.event.ShowAccountOptionsViewEvent;
import de.uol.swp.client.auth.events.ShowLoginViewEvent;
import de.uol.swp.client.credit.CreditPresenter;
import de.uol.swp.client.credit.event.ShowCreditViewEvent;
import de.uol.swp.client.main.AccountMenuPresenter;
import de.uol.swp.client.lobby.presenter.LobbyPresenter;
import de.uol.swp.client.lobby.event.JoinOrCreateCanceledEvent;
import de.uol.swp.client.lobby.presenter.JoinOrCreatePresenter;
import de.uol.swp.client.lobby.event.ShowJoinOrCreateViewEvent;
import de.uol.swp.client.lobby.event.CreateLobbyCanceledEvent;
import de.uol.swp.client.lobby.presenter.CreateLobbyPresenter;
import de.uol.swp.client.lobby.event.ShowCreateLobbyViewEvent;
import de.uol.swp.client.lobby.event.ShowLobbyViewEvent;
import de.uol.swp.client.main.MainMenuPresenter;
import de.uol.swp.client.main.event.ShowMainMenuViewEvent;
import de.uol.swp.client.register.RegistrationPresenter;
import de.uol.swp.client.register.event.RegistrationCanceledEvent;
import de.uol.swp.client.register.event.RegistrationErrorEvent;
import de.uol.swp.client.register.event.ShowRegistrationViewEvent;
import de.uol.swp.client.rulebook.RulebookPresenter;
import de.uol.swp.client.rulebook.event.ShowRulebookViewEvent;
import de.uol.swp.common.user.User;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;

/**
 * Class that manages which window/scene is currently shown
 *
 * @author Marco Grawunder
 * @since 2019-09-03
 */
public class SceneManager {

    static final Logger LOG = LogManager.getLogger(SceneManager.class);
    static final String STYLE_SHEET = "css/swp.css";

    static final String DIALOG_STYLE_SHEET = "css/myDialog.css";
    static final String BASE_VIEW_STYLE_SHEET = "css/BaseViewStyle.css";

    private final Stage primaryStage;
    private Scene loginScene;
    private String lastTitle;
    private Scene registrationScene;
    private Scene lobbyScene;
    private Scene joinOrCreateScene;
    private Scene createLobbyScene;
    private Scene mainScene;
    private Scene creditScene;
    private Scene rulebookScene;
    private Scene lastScene = null;
    private Scene currentScene = null;

    private Scene changeAccountOptionsScene;

    private final Injector injector;

    @Inject
    public SceneManager(EventBus eventBus, Injector injected, @Assisted Stage primaryStage) throws IOException {
        eventBus.register(this);
        this.primaryStage = primaryStage;
        this.injector = injected;
        initViews();
    }

    /**
     * Subroutine to initialize all views
     *
     * This is a subroutine of the constructor to initialize all views
     * @since 2019-09-03
     */
    private void initViews() throws IOException {
        initLoginView();
        initMainView();
        initCreditView();
        initRulebookView();
        initRegistrationView();
        initAccountOptionsView();
        initLobbyView();
        initJoinOrCreateView();
        initCreateLobbyView();
    }



    /**
     * Subroutine creating parent panes from FXML files
     *
     * This Method tries to create a parent pane from the FXML file specified by
     * the URL String given to it. If the LOG-Level is set to Debug or higher loading
     * is written to the LOG.
     * If it fails to load the view a RuntimeException is thrown.
     *
     * @param fxmlFile FXML file to load the view from
     * @return view loaded from FXML or null
     * @since 2019-09-03
     */
    private Parent initPresenter(String fxmlFile) throws IOException {
        Parent rootPane;
        FXMLLoader loader = injector.getInstance(FXMLLoader.class);
        try {
            URL url = getClass().getResource(fxmlFile);
            LOG.debug("Loading {}", url);
            loader.setLocation(url);
            rootPane = loader.load();
        } catch (Exception e) {
            throw new IOException(String.format("Could not load View! %s", e.getMessage()), e);
        }
        return rootPane;
    }

    /**
     * Initializes the main menu view
     *
     * If the mainScene is null it gets set to a new scene containing the
     * a pane showing the main menu view as specified by the MainMenuView
     * FXML file.
     *
     * @see de.uol.swp.client.main.MainMenuPresenter
     * @since 2019-09-03
     */
    private void initMainView() throws IOException {
        if (mainScene == null) {
           Parent rootPane = initPresenter(MainMenuPresenter.FXML);
            mainScene = new Scene(rootPane);
            mainScene.getStylesheets().add(BASE_VIEW_STYLE_SHEET);
        }
    }

    /**
     * Initializes the rulebook view
     *
     * If the rulebookScene is null it gets set to a new scene containing the
     * a pane showing the rulebook view as specified by the RulebookView
     * FXML file.
     *
     * @see de.uol.swp.client.rulebook.RulebookPresenter
     * @since 2022-11-27
     */
    private void initRulebookView() throws IOException {
        if (rulebookScene == null) {
            Parent rootPane = initPresenter(RulebookPresenter.FXML);
            rulebookScene = new Scene(rootPane);
            rulebookScene.getStylesheets().add(BASE_VIEW_STYLE_SHEET);
        }
    }

    /**
     * Initializes the credit view
     *
     * If the creditScene is null it gets set to a new scene containing the
     * a pane showing the credit view as specified by the CreditView
     * FXML file.
     *
     * @see de.uol.swp.client.credit.CreditPresenter
     * @since 2022-11-29
     */
    private void initCreditView() throws IOException {
        if (creditScene == null) {
            Parent rootPane = initPresenter(CreditPresenter.FXML);
            creditScene = new Scene(rootPane);
            creditScene.getStylesheets().add(BASE_VIEW_STYLE_SHEET);
        }
    }

    /**
     * Initializes the login view
     *
     * If the loginScene is null it gets set to a new scene containing the
     * a pane showing the login view as specified by the LoginView FXML file.
     *
     * @see de.uol.swp.client.auth.LoginPresenter
     * @since 2019-09-03
     */
    private void initLoginView() throws IOException {
        if (loginScene == null) {
            Parent rootPane = initPresenter(LoginPresenter.FXML);
            loginScene = new Scene(rootPane);
            loginScene.getStylesheets().add(BASE_VIEW_STYLE_SHEET);
        }
    }

    /**
     * Initializes the registration view
     *
     * If the registrationScene is null it gets set to a new scene containing the
     * a pane showing the registration view as specified by the RegistrationView
     * FXML file.
     *
     * @see de.uol.swp.client.register.RegistrationPresenter
     * @since 2019-09-03
     */
    private void initRegistrationView() throws IOException {
        if (registrationScene == null){
            Parent rootPane = initPresenter(RegistrationPresenter.FXML);
            registrationScene = new Scene(rootPane);
            registrationScene.getStylesheets().add(STYLE_SHEET);
        }
    }

    /**
     * Handles ShowCreditViewEvent detected on the EventBus
     *
     * If a ShowCreditViewEvent is detected on the EventBus, this method gets
     * called. It calls a method to switch the current screen to the credit
     * screen.
     *
     * @param event The ShowCreditViewEvent detected on the EventBus
     * @see de.uol.swp.client.credit.event.ShowCreditViewEvent
     * @since 2022-11-29
     */
    @Subscribe
    public void onShowCreditViewEvent(ShowCreditViewEvent event){
        showCreditScreen();
    }


    /**
     * Handles ShowRulebookViewEvent detected on the EventBus
     *
     * If a ShowRulebookViewEvent is detected on the EventBus, this method gets
     * called. It calls a method to switch the current screen to the rulebook
     * screen.
     *
     * @param event The ShowRulebookViewEvent detected on the EventBus
     * @see de.uol.swp.client.rulebook.event.ShowRulebookViewEvent
     * @since 2022-11-27
     */
    @Subscribe
    public void onShowRulebookViewEvent(ShowRulebookViewEvent event){
        showRulebookScreen();
    }


    /**
     * Initializes the account view
     *
     * If the changeAccountOptionsScene is null it gets set to a new scene containing the
     * pane showing the account view as specified by the AccountView FXML file.
     *
     * @see de.uol.swp.client.main.AccountMenuPresenter
     * @since 2022-11-25
     */
    private void initAccountOptionsView() throws IOException {
        if(changeAccountOptionsScene == null) {
            Parent rootPane = initPresenter(AccountMenuPresenter.FXML);
            changeAccountOptionsScene = new Scene(rootPane);
            changeAccountOptionsScene.getStylesheets().add(BASE_VIEW_STYLE_SHEET);
        }
    }

    /**
     * Initializes the lobby view
     *
     * If the lobbyScene is null it gets set to a new scene containing
     * a pane showing the lobby view as specified by the lobbyView
     * FXML file.
     *
     * @see de.uol.swp.client.lobby.presenter.LobbyPresenter
     * @since 2022-11-30
     */
    private void initLobbyView() throws IOException {
        if (lobbyScene == null){
            Parent rootPane = initPresenter(LobbyPresenter.FXML);
            lobbyScene = new Scene(rootPane, 1600,900);
            lobbyScene.getStylesheets().add(STYLE_SHEET);
        }
    }

    /**
     * Initializes the joinOrCreate view
     *
     * If the joinOrCreateScene is null it gets set to a new scene containing
     * a pane showing the joinOrCreate view as specified by the JoinOrCreateView
     * FXML file.
     *
     * @see de.uol.swp.client.register.RegistrationPresenter
     * @since 2022-11-30
     */
    private void initJoinOrCreateView() throws IOException {
        if (joinOrCreateScene == null){
            Parent rootPane = initPresenter(JoinOrCreatePresenter.FXML);
            joinOrCreateScene = new Scene(rootPane, 1600,900);
            joinOrCreateScene.getStylesheets().add(STYLE_SHEET);
        }
    }

    /**
     * Initializes the createLobby view
     *
     * If the createLobbyScene is null it gets set to a new scene containing
     * a pane showing the createLobby view as specified by the CreateLobbyView
     * FXML file.
     *
     * @see de.uol.swp.client.register.RegistrationPresenter
     * @since 2022-11-30
     */
    private void initCreateLobbyView() throws IOException {
        if (createLobbyScene == null){
            Parent rootPane = initPresenter(CreateLobbyPresenter.FXML);
            createLobbyScene = new Scene(rootPane, 400,200);
            createLobbyScene.getStylesheets().add(STYLE_SHEET);
        }
    }

    // -----------------------------------------------------
    // MainManu_Events
    // -----------------------------------------------------

    /**
     * Handles ShowRegistrationViewEvent detected on the EventBus
     *
     * If a ShowRegistrationViewEvent is detected on the EventBus, this method gets
     * called. It calls a method to switch the current screen to the registration
     * screen.
     *
     * @param event The ShowRegistrationViewEvent detected on the EventBus
     * @see de.uol.swp.client.register.event.ShowRegistrationViewEvent
     * @since 2019-09-03
     */
    @Subscribe
    public void onShowRegistrationViewEvent(ShowRegistrationViewEvent event){
        showRegistrationScreen();
    }

    /**
     * Handles ShowLoginViewEvent detected on the EventBus
     *
     * If a ShowLoginViewEvent is detected on the EventBus, this method gets
     * called. It calls a method to switch the current screen to the login screen.
     *
     * @param event The ShowLoginViewEvent detected on the EventBus
     * @see de.uol.swp.client.auth.events.ShowLoginViewEvent
     * @since 2019-09-03
     */
    @Subscribe
    public void onShowLoginViewEvent(ShowLoginViewEvent event){
        showLoginScreen();
    }

    /**
     * Handles RegistrationCanceledEvent detected on the EventBus
     *
     * If a RegistrationCanceledEvent is detected on the EventBus, this method gets
     * called. It calls a method to show the screen shown before registration.
     *
     * @param event The RegistrationCanceledEvent detected on the EventBus
     * @see de.uol.swp.client.register.event.RegistrationCanceledEvent
     * @since 2019-09-03
     */
    @Subscribe
    public void onRegistrationCanceledEvent(RegistrationCanceledEvent event){
        showScene(lastScene, lastTitle);
    }

    /**
     * Handles RegistrationErrorEvent detected on the EventBus
     *
     * If a RegistrationErrorEvent is detected on the EventBus, this method gets
     * called. It shows the error message of the event in a error alert.
     *
     * @param event The RegistrationErrorEvent detected on the EventBus
     * @see de.uol.swp.client.register.event.RegistrationErrorEvent
     * @since 2019-09-03
     */
    @Subscribe
    public void onRegistrationErrorEvent(RegistrationErrorEvent event) {
        showError(event.getMessage());
    }

    /**
     * Handles ShowAccountOptionsViewEvent detected on the EventBus
     *
     * If a ShowAccountOptionsViewEvent is detected on the EventBus, this method gets
     * called. It shows the AccountOptionView.
     *
     * @param event The ShowAccountOptionsViewEvent detected on the EventBus
     * @see de.uol.swp.client.main.event.ShowAccountOptionsViewEvent
     * @since 2022-12-03
     * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
     */
    @Subscribe
    public void onShowAccountOptionsViewEvent(ShowAccountOptionsViewEvent event) {
        showAccountOptionScreen();
    }

    /**
     * Handles ShowMainMenuViewEvent detected on the EventBus
     *
     * If a ShowMainMenuViewEvent is detected on the EventBus, this method gets
     * called. It calls a method to switch the current screen to the main manu screen.
     *
     * @param event The ShowMainMenuViewEvent detected on the EventBus
     * @see de.uol.swp.client.main.event.ShowMainMenuViewEvent
     * @since 2022-11-22
     */
    @Subscribe
    public void onShowMainMenuViewEvent(ShowMainMenuViewEvent event){
        showMainScreen(event.getUser());
    }

    // -----------------------------------------------------
    // FindCreate_Events
    // -----------------------------------------------------

    /**
     * Handles ShowFindCreateViewEvent detected on the EventBus
     *
     * If a ShowFindCreateViewEvent is detected on the EventBus, this method gets
     * called.
     *
     * @param event The ShowFindCreateViewEvent detected on the EventBus
     * @see ShowJoinOrCreateViewEvent
     * @since 2022-11-17
     */
    @Subscribe
    public void onShowFindCreateViewEvent(ShowJoinOrCreateViewEvent event){
        showJoinOrCreateScreen();
    }

    /**
     * Handles FindCreateCanceledEvent detected on the EventBus
     *
     * If a FindCreateCanceledEvent is detected on the EventBus, this method gets
     * called.
     *
     * @param event The FindCreateCanceledEvent detected on the EventBus
     * @see JoinOrCreateCanceledEvent
     * @since 2022-11-19
     */
    @Subscribe
    public void onJoinOrCreateCanceledEvent(JoinOrCreateCanceledEvent event){
        showScene(lastScene, lastTitle);
    }

    // -----------------------------------------------------
    // Lobby_Events
    // -----------------------------------------------------

    /**
     * Handles ShowLobbyViewEvent detected on the EventBus
     *
     * If a ShowLobbyViewEvent is detected on the EventBus, this method gets
     * called.
     *
     * @param event The ShowLobbyViewEvent detected on the EventBus
     * @see de.uol.swp.client.lobby.event.ShowLobbyViewEvent
     * @since 2022-11-15
     */
    @Subscribe
    public void onShowLobbyViewEvent(ShowLobbyViewEvent event) {
        showLobbyViewScreen();
    }

    // -----------------------------------------------------
    // CreateLobby_Events
    // -----------------------------------------------------

    /**
     * Handles CreateLobbyCanceledEvent detected on the EventBus
     *
     * If a CreateLobbyCanceledEvent is detected on the EventBus, this method gets
     * called.
     *
     * @param event The CreateLobbyCanceledEvent detected on the EventBus
     * @see de.uol.swp.client.lobby.event.CreateLobbyCanceledEvent
     * @since 2022-11-15
     */
    @Subscribe
    public void onCreateLobbyCanceledEvent(CreateLobbyCanceledEvent event){
        showScene(lastScene, lastTitle);
    }

    /**
     * Handles ShowCreateLobbyViewEvent detected on the EventBus
     *
     * If a ShowCreateLobbyViewEvent is detected on the EventBus, this method gets
     * called.
     *
     * @param event The RegistrationCanceledEvent detected on the EventBus
     * @see de.uol.swp.client.lobby.event.ShowCreateLobbyViewEvent
     * @since 2022-11-17
     */
    @Subscribe
    public void onShowCreateLobbyViewEvent(ShowCreateLobbyViewEvent event){
        showCreateLobbyScreen();
    }

    // -----------------------------------------------------
    // Error methods
    // -----------------------------------------------------

    /**
     * Shows an error message inside an error alert
     *
     * @param message The type of error to be shown
     * @param e       The error message
     * @since 2019-09-03
     */
    public void showError(String message, String e) {
        Platform.runLater(() -> {
            Alert a = new Alert(Alert.AlertType.ERROR, message + e);
            // based on: https://stackoverflow.com/questions/28417140/styling-default-javafx-dialogs/28421229#28421229
            DialogPane pane = a.getDialogPane();
            pane.getStylesheets().add(DIALOG_STYLE_SHEET);
            a.showAndWait();
        });
    }

    /**
     * Shows a server error message inside an error alert
     *
     * @param e The error message
     * @since 2019-09-03
     */
    public void showServerError(String e) {
        showError("Server returned an error:\n" , e);
    }

    /**
     * Shows an error message inside an error alert
     *
     * @param e The error message
     * @since 2019-09-03
     */
    public void showError(String e) {
        showError("Error:\n" , e);
    }

    /**
     * Switches the current scene and title to the given ones
     *
     * The current scene and title are saved in the lastScene and lastTitle variables,
     * before the new scene and title are set and shown.
     *
     * @param scene New scene to show
     * @param title New window title
     * @since 2019-09-03
     */
    private void showScene(final Scene scene, final String title) {
        this.lastScene = currentScene;
        this.lastTitle = primaryStage.getTitle();
        this.currentScene = scene;
        Platform.runLater(() -> {
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.centerOnScreen();
        });
    }

    /**
     * Shows the login error alert
     *
     * Opens an ErrorAlert popup saying "Error logging in to server"
     *
     * @since 2019-09-03
     */
    public void showLoginErrorScreen() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error logging in to server");
            // based on: https://stackoverflow.com/questions/28417140/styling-default-javafx-dialogs/28421229#28421229
            DialogPane pane = alert.getDialogPane();
            pane.getStylesheets().add(DIALOG_STYLE_SHEET);
            alert.showAndWait();
            showLoginScreen();
        });
    }

    /**
     * Shows the main menu
     *
     * Switches the current Scene to the mainScene and sets the title of
     * the window to "Welcome " and the username of the current user
     *
     * @since 2019-09-03
     */
    public void showMainScreen(User currentUser) {
        showScene(mainScene, "Welcome " + currentUser.getUsername());
    }

    /**
     * Shows the rulebook screen
     *
     * Switches the main menu Scene to the rulebookScene and sets the title of
     * the window to "Rulebook"
     *
     * @since 2022-11-27
     */
    public void showRulebookScreen() {
        showScene(rulebookScene, "Rulebook");
    }

    /**
     * Shows the credit screen
     *
     * Switches the main menu Scene to the creditScene and sets the title of
     * the window to "Credits"
     *
     * @since 2022-11-29
     */
    public void showCreditScreen() {
        showScene(creditScene, "Credits");
    }

    /**
     * Shows the login screen
     *
     * Switches the current Scene to the loginScene and sets the title of
     * the window to "Login"
     *
     * @since 2019-09-03
     */
    public void showLoginScreen() {
        showScene(loginScene,"Login");
    }

    /**
     * Shows the account screen
     *
     * Switches the current Scene to the accountScene and sets the title of
     * the window to "Account options"
     *
     * @since 2022-12-01
     */
    public void showAccountOptionScreen() {
        showScene(changeAccountOptionsScene, "Account options");
    }

    /**
     * Shows the registration screen
     *
     * Switches the current Scene to the registrationScene and sets the title of
     * the window to "Registration"
     *
     * @since 2019-09-03
     */
    public void showRegistrationScreen() {
        showScene(registrationScene,"Registration");
    }

    /**
     * Shows the joinOrCreate screen
     *
     * Switches the current Scene to the joinOrCreateScene and sets the title of
     * the window to "Lobbies"
     *
     * @since 2022-11-30
     */
    public void showJoinOrCreateScreen() {
        showScene(joinOrCreateScene,"Lobbies");
    }

    /**
     * Shows the createLobby screen
     *
     * Switches the current Scene to the createLobbyScene and sets the title of
     * the window to "Create Lobby"
     *
     * @since 2022-11-30
     */
    public void showCreateLobbyScreen() {
        showScene(createLobbyScene,"Create Lobby");
    }

    /**
     * Shows the lobby screen
     *
     * Switches the current Scene to the lobbyScene and sets the title of
     * the window to "Lobby"
     *
     * @since 2022-11-30
     */
    public void showLobbyViewScreen() {
        showScene(lobbyScene,"Lobby");
    }

}
