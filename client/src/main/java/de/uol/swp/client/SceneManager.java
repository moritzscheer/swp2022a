package de.uol.swp.client;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.assistedinject.Assisted;
import de.uol.swp.client.auth.LoginPresenter;
import de.uol.swp.client.lobby.LobbyService;
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
import de.uol.swp.client.main.MainMenuPresenter;
import de.uol.swp.client.main.event.ShowMainMenuViewEvent;
import de.uol.swp.client.register.RegistrationPresenter;
import de.uol.swp.client.register.event.RegistrationCanceledEvent;
import de.uol.swp.client.register.event.RegistrationErrorEvent;
import de.uol.swp.client.register.event.ShowRegistrationViewEvent;
import de.uol.swp.client.rulebook.RulebookPresenter;
import de.uol.swp.client.rulebook.event.ShowRulebookViewEvent;
import de.uol.swp.client.tab.event.ShowTabViewEvent;
import de.uol.swp.client.tab.TabPresenter;
import de.uol.swp.client.tab.event.CreateLobbyTabEvent;
import de.uol.swp.client.tab.event.DeleteLobbyTabEvent;
import de.uol.swp.client.tab.event.ShowNodeEvent;
import de.uol.swp.common.lobby.response.LobbyCreatedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyDroppedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyJoinedSuccessfulResponse;
import de.uol.swp.common.lobby.response.LobbyLeftSuccessfulResponse;
import de.uol.swp.client.setting.SettingPresenter;
import de.uol.swp.client.setting.event.ShowSettingViewEvent;
import de.uol.swp.common.user.User;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.user.UserDTO;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that manages which window/scene is currently shown
 *
 * @author Marco Grawunder
 * @since 2019-09-03
 */
public class SceneManager {

    private final EventBus eventBus;
    static final Logger LOG = LogManager.getLogger(SceneManager.class);
    static final String STYLE_SHEET = "css/swp.css";

    static final String DIALOG_STYLE_SHEET = "css/myDialog.css";
    static final String BASE_VIEW_STYLE_SHEET = "css/BaseViewStyle.css";

    private final Stage primaryStage;

    private String lastTitle;

    private Scene lastScene = null;
    private Scene currentScene = null;
    private Scene loginScene;
    private Scene registrationScene;
    private Scene tabScene;

    private Parent lastParent;
    private Parent currentParent;
    private Parent lobbyParent;
    private Parent joinOrCreateParent;
    private Parent createLobbyParent;
    private Parent mainParent;
    private Parent creditParent;
    private Parent rulebookParent;
    private Parent changeAccountOptionsParent;
    private Parent settingParent;

    @Inject
    private LobbyService lobbyService;
    @Inject
    private LobbyPresenterFactory lobbyPresenterFactory;
    private LobbyPresenter lobbyPresenter;
    private final Map<Integer, LobbyPresenter> lobbyPresenterMap = new HashMap<>();
    private double screenSizeWidth;
    private double screenSizeHeight;
    private double lastSceneWidth;
    private double lastSceneHeight;
    private Scene changeAccountOptionsScene;

    private final Injector injector;

    @Inject
    public SceneManager(EventBus eventBus, Injector injected, @Assisted Stage primaryStage) throws IOException {
        eventBus.register(this);
        this.eventBus = eventBus;
        this.primaryStage = primaryStage;

        /**
         * Enables the screen to be resizeable
         *
         * @author Tommy Dang
         * @since 2022-12-15
         */
        primaryStage.setResizable(true);

        /**
         * Set the screen in maximized window
         *
         * @author Tommy Dang
         * @since 2022-12-15
         */
        primaryStage.setMaximized(true);

        /**
         * Set the minimum size of the stage
         *
         * @author Tommy Dang
         * @since 2022-12-23
         */
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);

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
        initRegistrationView();
        initTabView();
        initMainView();
        initCreditView();
        initRulebookView();
        initSettingView();
        initAccountOptionsView();
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

    // -----------------------------------------------------
    // init views
    // -----------------------------------------------------

    /**
     * Initializes the login view
     *
     * If the loginScene is null it gets set to a new scene containing
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
     * If the registrationScene is null it gets set to a new scene containing
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
     * Initializes the tab view
     *
     * If the tabScene is null it gets set to a new scene containing
     * a pane showing the tab view as specified by the TabView
     * FXML file.
     *
     * @see de.uol.swp.client.register.RegistrationPresenter
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    private void initTabView() throws IOException {
        if (tabScene == null) {
            Parent rootPane = initPresenter(TabPresenter.FXML);
            tabScene = new Scene(rootPane, screenSizeWidth, screenSizeHeight);
            tabScene.getStylesheets().add(BASE_VIEW_STYLE_SHEET);
        }
    }

    /**
     * Initializes the setting view
     *
     * If the settingParent is null it gets set to a new scene containing the
     * a pane showing the setting view as specified by the SettingView
     * FXML file.
     *
     * @see de.uol.swp.client.setting.SettingPresenter
     * @since 2022-12-11
     */
    private void initSettingView() throws IOException {
        if (settingParent == null) {
            settingParent = initPresenter(SettingPresenter.FXML);
        }
    }

    /**
     * Initializes the main menu view
     *
     * If the mainParent is null it gets set to a new Parent showing the main menu view
     * as specified by the MainMenuView FXML file.
     *
     * @see de.uol.swp.client.main.MainMenuPresenter
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    private void initMainView() throws IOException {
        if (mainParent == null) {
            mainParent = initPresenter(MainMenuPresenter.FXML);
        }
    }

    /**
     * Initializes the rule book view
     *
     * If the rulebookParent is null it gets set to a new Parent showing the rule book view
     * as specified by the RuleBookView FXML file.
     *
     * @see de.uol.swp.client.rulebook.RulebookPresenter
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    private void initRulebookView() throws IOException {
        if (rulebookParent == null) {
            rulebookParent = initPresenter(RulebookPresenter.FXML);
        }
    }

    /**
     * Initializes the credit view
     *
     * If the creditParent is null it gets set to a new Parent showing the credit view
     * as specified by the RuleBookView FXML file.
     *
     * @see de.uol.swp.client.credit.CreditPresenter
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    private void initCreditView() throws IOException {
        if (creditParent == null) {
            creditParent = initPresenter(CreditPresenter.FXML);
        }
    }

    /**
     * Initializes the change account option view
     *
     * If the changeAccountOptionsParent is null it gets set to a new Parent showing the change account option view
     * as specified by the changeAccountOptionView FXML file.
     *
     * @see de.uol.swp.client.main.AccountMenuPresenter
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    private void initAccountOptionsView() throws IOException {
        if(changeAccountOptionsParent == null) {
            changeAccountOptionsParent = initPresenter(AccountMenuPresenter.FXML);
        }
    }

    /**
     * Initializes the join or create view
     *
     * If the lobbyParent is null it gets set to a new Parent showing the join or create view
     * as specified by the JoinOrCreate FXML file.
     *
     * @see de.uol.swp.client.register.RegistrationPresenter
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    private void initJoinOrCreateView() throws IOException {
        if (joinOrCreateParent == null){
            joinOrCreateParent = initPresenter(JoinOrCreatePresenter.FXML);
        }
    }

    /**
     * Initializes the create lobby view
     *
     * If the lobbyParent is null it gets set to a new Parent showing the join or create lobby view
     * as specified by the CreateLobby FXML file.
     *
     * @see de.uol.swp.client.register.RegistrationPresenter
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    private void initCreateLobbyView() throws IOException {
        if (createLobbyParent == null){
            createLobbyParent = initPresenter(CreateLobbyPresenter.FXML);
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
     * @author Moritz Scheer
     * @since 2022-11-30
     */
    private void initLobbyView() throws IOException {
        if (lobbyParent == null){
            lobbyParent = initPresenter(LobbyPresenter.FXML);
        }
    }

    // -----------------------------------------------------
    // subscribe methods
    // -----------------------------------------------------

    /**
     * Handles successfully created Lobbies
     *
     * If an LobbyCreatedSuccessfulResponse object is detected on the EventBus this
     * method is called. It calls a private method to set up a tab.
     *
     * @param message The LobbyCreatedSuccessfulResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onLobbyCreatedSuccessfulResponse(LobbyCreatedSuccessfulResponse message) {
        setLobbyTab(message.getLobby(), message.getUser());
    }

    /**
     * Handles successfully joined Lobbies
     *
     * If an LobbyJoinedSuccessfulResponse object is detected on the EventBus this
     * method is called. It calls a private method to set up a tab.
     *
     * @param message The LobbyJoinedSuccessfulResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onLobbyJoinedSuccessfulResponse(LobbyJoinedSuccessfulResponse message) {
        setLobbyTab(message.getLobby(), message.getUser());
    }

    /**
     * Handles successfully left Lobbies
     *
     * If an LobbyLeaveUserResponse object is detected on the EventBus this
     * method is called. It calls a private method to close a tab.
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
     * Handles successfully dropped Lobbies
     *
     * If an LobbyDroppedResponse object is detected on the EventBus this
     * method is called. It calls a private method to close a tab.
     *
     * @param message The LobbyDroppedResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onLobbyDroppedResponse(LobbyDroppedSuccessfulResponse message) {
        deleteLobbyTab(message.getLobbyID());
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
     * Handles ShowSettingViewEvent detected on the EventBus
     *
     * If a ShowSettingViewEvent is detected on the EventBus, this method gets
     * called. It calls a method to switch the current screen to the setting
     * screen.
     *
     * @param event The ShowSettingViewEvent detected on the EventBus
     * @see de.uol.swp.client.setting.event.ShowSettingViewEvent
     * @since 2022-11-27
     */
    @Subscribe
    public void onShowSettingViewEvent(ShowSettingViewEvent event){
        showSettingScreen();
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
     * called. It shows the error message of the event in an error alert.
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
        showMainScreen();
    }

    /**
     * Handles ShowJoinOrCreateViewEvent detected on the EventBus
     *
     * If a ShowJoinOrCreateViewEvent is detected on the EventBus, this method gets
     * called.
     *
     * @param event The ShowJoinOrCreateViewEvent detected on the EventBus
     * @see de.uol.swp.client.lobby.event.ShowJoinOrCreateViewEvent
     * @since 2022-11-17
     */
    @Subscribe
    public void onShowJoinOrCreateViewEvent(ShowJoinOrCreateViewEvent event){
        showJoinOrCreateScreen();
    }

    /**
     * Handles JoinOrCreateCanceledEvent detected on the EventBus
     *
     * If a JoinOrCreateCanceledEvent is detected on the EventBus, this method gets
     * called.
     *
     * @param event The JoinOrCreateCanceledEvent detected on the EventBus
     * @see de.uol.swp.client.lobby.event.JoinOrCreateCanceledEvent
     * @since 2022-11-19
     */
    @Subscribe
    public void onJoinOrCreateCanceledEvent(JoinOrCreateCanceledEvent event){
        showMainScreen();
    }

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
        showJoinOrCreateScreen();
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

    /**
     * Handles ShowTabViewEvent detected on the EventBus
     *
     * If a ShowTabViewEvent is detected on the EventBus, this method gets
     * called. It shows the TabView.
     *
     * @param event The ShowTabViewEvent detected on the EventBus
     * @see de.uol.swp.client.tab.event.ShowTabViewEvent
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onShowTabViewEvent(ShowTabViewEvent event) {
        showTabScreen(event.getUser());
    }

    /**
     * Handles ShowCreateLobbyViewEvent detected on the EventBus
     *
     * If a ShowCreateLobbyViewEvent is detected on the EventBus, this method gets
     * called.
     *
     * @param event The RegistrationCanceledEvent detected on the EventBus
     * @see de.uol.swp.client.CloseClientEvent
     * @since 2023-01-04
     */
    @Subscribe
    public void onCloseClientEvent(CloseClientEvent event){
        primaryStage.close();
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

    // -----------------------------------------------------
    // show methods
    // -----------------------------------------------------

    /**
     * Switches the current scene and title to the given ones and changes the width and height
     *
     * The current scene, title, screen width and height are saved in the lastScene, lastTitle, lastSceneWidth
     * and lastSceneHeight variables, before the new scene, title and their width and height are set and shown.
     *
     * @param scene New scene to show
     * @param title New window title
     * @author Tommy Dang
     * @since 2023-01-04
     */
    private void showScene(final Scene scene, final String title) {
        this.lastScene = currentScene;
        this.lastTitle = primaryStage.getTitle();
        this.currentScene = scene;
        this.lastSceneWidth = primaryStage.getWidth();
        this.lastSceneHeight = primaryStage.getHeight();
        Platform.runLater(() -> {
            primaryStage.setWidth(lastSceneWidth);
            primaryStage.setHeight(lastSceneHeight);
            primaryStage.setTitle(title);
            primaryStage.setScene(scene);
            primaryStage.show();
        });
    }

    /**
     * Switches the current Parent in the current tab that is given to
     *
     * The current scene and title are saved in the lastScene and lastTitle variables,
     * before the new scene and title are set and shown.
     *
     * @param tab Integer containing the lobbyID and also the tabID
     * @param parent New Parent to show
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    private void showNode(int tab, Parent parent) {
        this.lastParent = currentParent;
        this.lastTitle = primaryStage.getTitle();
        this.currentParent = parent;
        eventBus.post(new ShowNodeEvent(tab, parent));
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

    // -----------------------------------------------------
    // showScreen methods
    // -----------------------------------------------------

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
     * Shows the tab screen
     *
     * Switches the current Scene to the tabScene and sets the title of
     * the window to "User: (username)" and also show the main menu node in the tabScene.
     *
     * @since 2019-09-03
     */
    public void showTabScreen(User user) {
        showScene(tabScene, "User: " + user.getUsername());
        showNode(0, mainParent);
    }

    /**
     * Shows the main menu
     *
     * Switches the current Scene to the mainScene and sets the title of
     * the window to "Welcome " and the username of the current user
     *
     * @since 2019-09-03
     */
    public void showMainScreen() {
        showNode(0, mainParent);
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
        showNode(0, rulebookParent);
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
        showNode(0, creditParent);
    }

    /**
     * Shows the setting screen
     *
     * Switches the main menu Scene to the settingParent and sets the title of
     * the window to "Settings"
     *
     * @since 2022-12-11
     */
    public void showSettingScreen() {
        showNode(0, settingParent);
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
        showNode(0, changeAccountOptionsParent);
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
        showNode(0, joinOrCreateParent);
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
        showNode(0, createLobbyParent);
    }

    /**
     * helper method to set up the lobby view
     *
     * This method initializes the lobby view and assigns an lobbyPresenter to the view. Then it saves all the current
     * information in the lobbyPresenter and posts an Event on the Eventbus to create a tab in the TabPresenter.
     *
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    private void setLobbyTab(LobbyDTO lobby, UserDTO user) {
        try {
            // load File and Controller
            lobbyParent = initLobbyPresenter(lobby.getLobbyID());

            // update Information in Controller
            lobbyPresenterMap.get(lobby.getLobbyID()).updateInformation(lobby, user);

            // show main menu if lobby is singleplayer, else it shows the joinOrCreate view
            if(lobby.isMultiplayer()) {
                showJoinOrCreateScreen();
            } else {
                showMainScreen();
            }

            // create new Tab and switch to the tab
            eventBus.post(new CreateLobbyTabEvent(lobby, lobbyParent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * helper method to delete a lobby view
     *
     * This method posts an Event on the Eventbus to delete a tab in the TabPresenter.
     *
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    private void deleteLobbyTab(Integer lobbyID) {
        eventBus.post(new DeleteLobbyTabEvent(lobbyID));
    }
}
