package de.uol.swp.client;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.assistedinject.Assisted;

import de.uol.swp.client.auth.LoginPresenter;
import de.uol.swp.client.auth.events.ShowLoginViewEvent;
import de.uol.swp.client.credit.CreditPresenter;
import de.uol.swp.client.credit.event.ShowCreditViewEvent;
import de.uol.swp.client.lobby.LobbyManagement;
import de.uol.swp.client.lobby.LobbyService;
import de.uol.swp.client.lobby.game.GameManagement;
import de.uol.swp.client.lobby.game.GameService;
import de.uol.swp.client.lobby.game.LobbyGame;
import de.uol.swp.client.lobby.game.events.ShowGameViewEvent;
import de.uol.swp.client.lobby.game.presenter.GamePresenter;
import de.uol.swp.client.lobby.lobby.event.ShowLobbyViewEvent;
import de.uol.swp.client.lobby.lobby.presenter.LobbyPresenter;
import de.uol.swp.client.main.AccountMenuPresenter;
import de.uol.swp.client.main.MainMenuPresenter;
import de.uol.swp.client.main.event.ShowAccountOptionsViewEvent;
import de.uol.swp.client.main.event.ShowMainMenuViewEvent;
import de.uol.swp.client.preLobby.events.CreateLobbyCanceledEvent;
import de.uol.swp.client.preLobby.events.JoinOrCreateCanceledEvent;
import de.uol.swp.client.preLobby.events.ShowCreateLobbyViewEvent;
import de.uol.swp.client.preLobby.events.ShowJoinOrCreateViewEvent;
import de.uol.swp.client.preLobby.presenter.CreateLobbyPresenter;
import de.uol.swp.client.preLobby.presenter.JoinOrCreatePresenter;
import de.uol.swp.client.register.RegistrationPresenter;
import de.uol.swp.client.register.event.RegistrationCanceledEvent;
import de.uol.swp.client.register.event.RegistrationErrorEvent;
import de.uol.swp.client.register.event.ShowRegistrationViewEvent;
import de.uol.swp.client.rulebook.RulebookPresenter;
import de.uol.swp.client.rulebook.event.ShowRulebookViewEvent;
import de.uol.swp.client.setting.SettingPresenter;
import de.uol.swp.client.setting.event.ShowSettingViewEvent;
import de.uol.swp.client.tab.TabPresenter;
import de.uol.swp.client.tab.event.ChangeElementEvent;
import de.uol.swp.common.game.dto.GameDTO;
import de.uol.swp.common.lobby.dto.LobbyDTO;
import de.uol.swp.common.lobby.response.*;
import de.uol.swp.common.user.User;
import de.uol.swp.common.user.UserDTO;

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
    private Parent gameParent;

    @Inject
    private TabPresenter tabPresenter;
    @Inject
    private LobbyManagement lobbyManagement;
    @Inject GameManagement gameManagement;
    @Inject
    private GameService gameService;
    @Inject
    private LobbyService lobbyService;
    @Inject
    private LobbyPresenterFactory lobbyPresenterFactory;
    @Inject
    private GamePresenterFactory gamePresenterFactory;

    //private GamePresenter gamePresenter;

    private double screenSizeWidth;
    private double screenSizeHeight;
    private double lastSceneWidth;
    private double lastSceneHeight;

    private final Injector injector;

    @Inject
    public SceneManager(EventBus eventBus, Injector injected, @Assisted Stage primaryStage)
            throws IOException {
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

        lobbyManagement = injector.getInstance(LobbyManagement.class);
        gameManagement = injector.getInstance(GameManagement.class);
        lobbyPresenterFactory = injector.getInstance(LobbyPresenterFactory.class);
        gamePresenterFactory = injector.getInstance(GamePresenterFactory.class);

        initViews();
    }

    /**
     * Subroutine to initialize all views
     *
     * <p>This is a subroutine of the constructor to initialize all views
     *
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
        initLobbyView();
        initGameView();
    }

    /**
     * Subroutine creating parent panes from FXML files
     *
     * <p>This Method tries to create a parent pane from the FXML file specified by the URL String
     * given to it. If the LOG-Level is set to Debug or higher loading is written to the LOG. If it
     * fails to load the view a RuntimeException is thrown.
     *
     * @param fxmlFile FXML file to load the view from
     * @return view loaded from FXML or null
     * @since 2019-09-03
     */
    private Parent initPresenter(String fxmlFile) throws IOException {
        Parent rootPane;
        FXMLLoader loader = injector.getInstance(FXMLLoader.class);
        if (fxmlFile.equals("/fxml/LobbyView.fxml")) {
            LobbyPresenter lobbyPresenter = lobbyPresenterFactory.create();
            lobbyManagement.setNextLobbyPresenter(lobbyPresenter);
            loader.setController(lobbyPresenter);
        } else if (fxmlFile.equals("/fxml/GameView.fxml")) {
            GamePresenter gamePresenter = gamePresenterFactory.create();
            lobbyManagement.setNextGamePresenter(gamePresenter);
            gameManagement.setNextGamePresenter(gamePresenter);
            loader.setController(gamePresenter);
        }
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
     * <p>If the loginScene is null it gets set to a new scene containing a pane showing the login
     * view as specified by the LoginView FXML file.
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
     * <p>If the registrationScene is null it gets set to a new scene containing a pane showing the
     * registration view as specified by the RegistrationView FXML file.
     *
     * @see de.uol.swp.client.register.RegistrationPresenter
     * @since 2019-09-03
     */
    private void initRegistrationView() throws IOException {
        if (registrationScene == null) {
            Parent rootPane = initPresenter(RegistrationPresenter.FXML);
            registrationScene = new Scene(rootPane);
            registrationScene.getStylesheets().add(STYLE_SHEET);
        }
    }

    /**
     * Initializes the tab view
     *
     * <p>If the tabScene is null it gets set to a new scene containing a pane showing the tab view
     * as specified by the TabView FXML file.
     *
     * @author Moritz Scheer
     * @see de.uol.swp.client.register.RegistrationPresenter
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
     * <p>If the settingParent is null it gets set to a new scene containing the a pane showing the
     * setting view as specified by the SettingView FXML file.
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
     * Initializes the game view
     *
     * <p>If the gameParent is null it gets set to a new Parent showing the game view as specified
     * by the GameView FXML file.
     *
     * @author Moritz Scheer
     * @see GamePresenter
     * @since 2023-02-20
     */
    private void initGameView() throws IOException {
        if (gameParent == null)
            gameParent = initPresenter(GamePresenter.FXML);
    }

    /**
     * Initializes the main menu view
     *
     * <p>If the mainParent is null it gets set to a new Parent showing the main menu view as
     * specified by the MainMenuView FXML file.
     *
     * @author Moritz Scheer
     * @see de.uol.swp.client.main.MainMenuPresenter
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
     * <p>If the rulebookParent is null it gets set to a new Parent showing the rule book view as
     * specified by the RuleBookView FXML file.
     *
     * @author Moritz Scheer
     * @see de.uol.swp.client.rulebook.RulebookPresenter
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
     * <p>If the creditParent is null it gets set to a new Parent showing the credit view as
     * specified by the RuleBookView FXML file.
     *
     * @author Moritz Scheer
     * @see de.uol.swp.client.credit.CreditPresenter
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
     * <p>If the changeAccountOptionsParent is null it gets set to a new Parent showing the change
     * account option view as specified by the changeAccountOptionView FXML file.
     *
     * @author Moritz Scheer
     * @see de.uol.swp.client.main.AccountMenuPresenter
     * @since 2022-12-27
     */
    private void initAccountOptionsView() throws IOException {
        if (changeAccountOptionsParent == null) {
            changeAccountOptionsParent = initPresenter(AccountMenuPresenter.FXML);
        }
    }

    /**
     * Initializes the join or create view
     *
     * <p>If the lobbyParent is null it gets set to a new Parent showing the join or create view as
     * specified by the JoinOrCreate FXML file.
     *
     * @author Moritz Scheer
     * @see de.uol.swp.client.register.RegistrationPresenter
     * @since 2022-12-27
     */
    private void initJoinOrCreateView() throws IOException {
        if (joinOrCreateParent == null) {
            joinOrCreateParent = initPresenter(JoinOrCreatePresenter.FXML);
        }
    }

    /**
     * Initializes the create lobby view
     *
     * <p>If the lobbyParent is null it gets set to a new Parent showing the join or create lobby
     * view as specified by the CreateLobby FXML file.
     *
     * @author Moritz Scheerini
     * @see de.uol.swp.client.register.RegistrationPresenter
     * @since 2022-12-27
     */
    private void initCreateLobbyView() throws IOException {
        if (createLobbyParent == null) {
            createLobbyParent = initPresenter(CreateLobbyPresenter.FXML);
        }
    }

    /**
     * Initializes the lobby view
     *
     * <p>If the lobbyScene is null it gets set to a new scene containing a pane showing the lobby
     * view as specified by the lobbyView FXML file.
     *
     * @author Moritz Scheer
     * @see LobbyPresenter
     * @since 2022-11-30
     */
    private void initLobbyView() throws IOException {
        lobbyParent = initPresenter(LobbyPresenter.FXML);
    }

    // -----------------------------------------------------
    // subscribe methods
    // -----------------------------------------------------

    /**
     * Handles successfully created Lobbies
     *
     * <p>If an LobbyCreatedSuccessfulResponse object is detected on the EventBus this method is
     * called. It calls a private method to set up a tab.
     *
     * @param message The LobbyCreatedSuccessfulResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onLobbyCreatedSuccessfulResponse(LobbyCreatedSuccessfulResponse message) {
        createTab(message.getLobby(), message.getUser());
    }

    /**
     * Handles successfully joined Lobbies
     *
     * <p>If an LobbyJoinedSuccessfulResponse object is detected on the EventBus this method is
     * called. It calls a private method to set up a tab.
     *
     * @param message The LobbyJoinedSuccessfulResponse object detected on the EventBus
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    @Subscribe
    public void onLobbyJoinedSuccessfulResponse(LobbyJoinedSuccessfulResponse message) {
        createTab(message.getLobby(), message.getUser());
    }

    /**
     * Handles ShowCreditViewEvent detected on the EventBus
     *
     * <p>If a ShowCreditViewEvent is detected on the EventBus, this method gets called. It calls a
     * method to switch the current screen to the credit screen.
     *
     * @param event The ShowCreditViewEvent detected on the EventBus
     * @see de.uol.swp.client.credit.event.ShowCreditViewEvent
     * @since 2022-11-29
     */
    @Subscribe
    public void onShowCreditViewEvent(ShowCreditViewEvent event) {
        showCreditScreen();
    }

    /**
     * Handles ShowRulebookViewEvent detected on the EventBus
     *
     * <p>If a ShowRulebookViewEvent is detected on the EventBus, this method gets called. It calls
     * a method to switch the current screen to the rulebook screen.
     *
     * @param event The ShowRulebookViewEvent detected on the EventBus
     * @see de.uol.swp.client.rulebook.event.ShowRulebookViewEvent
     * @since 2022-11-27
     */
    @Subscribe
    public void onShowRulebookViewEvent(ShowRulebookViewEvent event) {
        showRulebookScreen();
    }

    /**
     * Handles ShowSettingViewEvent detected on the EventBus
     *
     * <p>If a ShowSettingViewEvent is detected on the EventBus, this method gets called. It calls a
     * method to switch the current screen to the setting screen.
     *
     * @param event The ShowSettingViewEvent detected on the EventBus
     * @see de.uol.swp.client.setting.event.ShowSettingViewEvent
     * @since 2022-11-27
     */
    @Subscribe
    public void onShowSettingViewEvent(ShowSettingViewEvent event) {
        showSettingScreen();
    }

    // -----------------------------------------------------
    // MainManu_Events
    // -----------------------------------------------------

    /**
     * Handles ShowRegistrationViewEvent detected on the EventBus
     *
     * <p>If a ShowRegistrationViewEvent is detected on the EventBus, this method gets called. It
     * calls a method to switch the current screen to the registration screen.
     *
     * @param event The ShowRegistrationViewEvent detected on the EventBus
     * @see de.uol.swp.client.register.event.ShowRegistrationViewEvent
     * @since 2019-09-03
     */
    @Subscribe
    public void onShowRegistrationViewEvent(ShowRegistrationViewEvent event) {
        showRegistrationScreen();
    }

    /**
     * Handles ShowLoginViewEvent detected on the EventBus
     *
     * <p>If a ShowLoginViewEvent is detected on the EventBus, this method gets called. It calls a
     * method to switch the current screen to the login screen.
     *
     * @param event The ShowLoginViewEvent detected on the EventBus
     * @see de.uol.swp.client.auth.events.ShowLoginViewEvent
     * @since 2019-09-03
     */
    @Subscribe
    public void onShowLoginViewEvent(ShowLoginViewEvent event) {
        showLoginScreen();
    }

    /**
     * Handles RegistrationCanceledEvent detected on the EventBus
     *
     * <p>If a RegistrationCanceledEvent is detected on the EventBus, this method gets called. It
     * calls a method to show the screen shown before registration.
     *
     * @param event The RegistrationCanceledEvent detected on the EventBus
     * @see de.uol.swp.client.register.event.RegistrationCanceledEvent
     * @since 2019-09-03
     */
    @Subscribe
    public void onRegistrationCanceledEvent(RegistrationCanceledEvent event) {
        showScene(lastScene, lastTitle);
    }

    /**
     * Handles RegistrationErrorEvent detected on the EventBus
     *
     * <p>If a RegistrationErrorEvent is detected on the EventBus, this method gets called. It shows
     * the error message of the event in an error alert.
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
     * <p>If a ShowAccountOptionsViewEvent is detected on the EventBus, this method gets called. It
     * shows the AccountOptionView.
     *
     * @param event The ShowAccountOptionsViewEvent detected on the EventBus
     * @author Waldemar Kempel and Maria Eduarda Costa Leite Andrade
     * @see de.uol.swp.client.main.event.ShowAccountOptionsViewEvent
     * @since 2022-12-03
     */
    @Subscribe
    public void onShowAccountOptionsViewEvent(ShowAccountOptionsViewEvent event) {
        showAccountOptionScreen();
    }

    /**
     * Handles ShowMainMenuViewEvent detected on the EventBus
     *
     * <p>If a ShowMainMenuViewEvent is detected on the EventBus, this method gets called. It calls
     * a method to switch the current screen to the main manu screen.
     *
     * @param event The ShowMainMenuViewEvent detected on the EventBus
     * @see de.uol.swp.client.main.event.ShowMainMenuViewEvent
     * @since 2022-11-22
     */
    @Subscribe
    public void onShowMainMenuViewEvent(ShowMainMenuViewEvent event) {
        showMainScreen();
    }

    /**
     * Handles ShowJoinOrCreateViewEvent detected on the EventBus
     *
     * <p>If a ShowJoinOrCreateViewEvent is detected on the EventBus, this method gets called.
     *
     * @param event The ShowJoinOrCreateViewEvent detected on the EventBus
     * @see ShowJoinOrCreateViewEvent
     * @since 2022-11-17
     */
    @Subscribe
    public void onShowJoinOrCreateViewEvent(ShowJoinOrCreateViewEvent event) {
        showJoinOrCreateScreen();
    }

    /**
     * Handles JoinOrCreateCanceledEvent detected on the EventBus
     *
     * <p>If a JoinOrCreateCanceledEvent is detected on the EventBus, this method gets called.
     *
     * @param event The JoinOrCreateCanceledEvent detected on the EventBus
     * @see JoinOrCreateCanceledEvent
     * @since 2022-11-19
     */
    @Subscribe
    public void onJoinOrCreateCanceledEvent(JoinOrCreateCanceledEvent event) {
        showMainScreen();
    }

    /**
     * Handles CreateLobbyCanceledEvent detected on the EventBus
     *
     * <p>If a CreateLobbyCanceledEvent is detected on the EventBus, this method gets called.
     *
     * @param event The CreateLobbyCanceledEvent detected on the EventBus
     * @see CreateLobbyCanceledEvent
     * @since 2022-11-15
     */
    @Subscribe
    public void onCreateLobbyCanceledEvent(CreateLobbyCanceledEvent event) {
        showJoinOrCreateScreen();
    }

    /**
     * Handles ShowCreateLobbyViewEvent detected on the EventBus
     *
     * <p>If a ShowCreateLobbyViewEvent is detected on the EventBus, this method gets called.
     *
     * @param event The RegistrationCanceledEvent detected on the EventBus
     * @see ShowCreateLobbyViewEvent
     * @since 2022-11-17
     */
    @Subscribe
    public void onShowCreateLobbyViewEvent(ShowCreateLobbyViewEvent event) {
        showCreateLobbyScreen();
    }

    /**
     * Handles ShowGameViewEvent detected on the EventBus
     *
     * <p>If a ShowGameViewEvent is detected on the EventBus, this method gets called.
     *
     * @param event The ShowGameViewEvent detected on the EventBus
     * @see ShowGameViewEvent
     * @since 2023-03-09
     */
    @Subscribe
    public void onShowGameViewEvent(ShowGameViewEvent event) {
        System.out.println("SceneManager.onShowGameViewEvent");
        createGameView(event.getLobby(), event.getGameID());
        showGameScreen(event.getLobbyID());
    }

    /**
     * Handles ShowLobbyViewEvent detected on the EventBus
     *
     * <p>If a ShowLobbyViewEvent is detected on the EventBus, this method gets called.
     *
     * @param event The ShowLobbyViewEvent detected on the EventBus
     * @see ShowLobbyViewEvent
     * @since 2023-03-09
     */
    @Subscribe
    public void onShowLobbyViewEvent(ShowLobbyViewEvent event) {
        showLobbyScreen(event.getLobbyID());
    }

    /**
     * Handles CloseClientEvent detected on the EventBus
     *
     * <p>If a CloseClientEvent is detected on the EventBus, this method gets called.
     *
     * @param event The CloseClientEvent detected on the EventBus
     * @see de.uol.swp.client.CloseClientEvent
     * @since 2023-01-04
     */
    @Subscribe
    public void onCloseClientEvent(CloseClientEvent event) {
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
        Platform.runLater(
                () -> {
                    Alert a = new Alert(Alert.AlertType.ERROR, message + e);
                    // based on:
                    // https://stackoverflow.com/questions/28417140/styling-default-javafx-dialogs/28421229#28421229
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
        showError("Server returned an error:\n", e);
    }

    /**
     * Shows an error message inside an error alert
     *
     * @param e The error message
     * @since 2019-09-03
     */
    public void showError(String e) {
        showError("Error:\n", e);
    }

    // -----------------------------------------------------
    // show methods
    // -----------------------------------------------------

    /**
     * Switches the current scene and title to the given ones and changes the width and height
     *
     * <p>The current scene, title, screen width and height are saved in the lastScene, lastTitle,
     * lastSceneWidth and lastSceneHeight variables, before the new scene, title and their width and
     * height are set and shown.
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
        Platform.runLater(
                () -> {
                    /**
                     * If the user wants to exit the client when he is logged-in, a pop-up is
                     * displayed
                     *
                     * @author Moritz Scheer
                     * @since 2023-01-25
                     */
                    if (currentScene.equals(tabScene)) {
                        primaryStage.setOnCloseRequest(
                                closeEvent -> {
                                    closeEvent.consume();
                                    if (tabPresenter.infoLabel3IsVisible()) {
                                        tabPresenter.updateInfoBox();
                                        eventBus.post(
                                                new ChangeElementEvent(tabPresenter.getTabID()));
                                    }
                                    tabPresenter.updateInfoBox();
                                    tabPresenter.setInfoLabel(2);
                                });
                    }
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
     * <p>The current scene and title are saved in the lastScene and lastTitle variables, before the
     * new scene and title are set and shown.
     *
     * @param tab    Integer containing the lobbyID and also the tabID
     * @param parent New Parent to show
     * @author Moritz Scheer
     * @since 2022-12-27
     */
    private void showNode(int tab, Parent parent) {
        this.lastParent = currentParent;
        this.lastTitle = primaryStage.getTitle();
        this.currentParent = parent;
        tabPresenter.showNode(tab, parent);
    }

    /**
     * Shows the login error alert
     *
     * <p>Opens an ErrorAlert popup saying "Error logging in to server"
     *
     * @since 2019-09-03
     */
    public void showLoginErrorScreen() {
        Platform.runLater(
                () -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Error logging in to server");
                    // based on:
                    // https://stackoverflow.com/questions/28417140/styling-default-javafx-dialogs/28421229#28421229
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
     * <p>Switches the current Scene to the registrationScene and sets the title of the window to
     * "Registration"
     *
     * @since 2019-09-03
     */
    public void showRegistrationScreen() {
        showScene(registrationScene, "Registration");
    }

    /**
     * Shows the login screen
     *
     * <p>Switches the current Scene to the loginScene and sets the title of the window to "Login"
     *
     * @since 2019-09-03
     */
    public void showLoginScreen() {
        showScene(loginScene, "Login");
    }

    /**
     * Shows the tab screen
     *
     * <p>Switches the current Scene to the tabScene and sets the title of the window to "User:
     * (username)" and also show the main menu node in the tabScene.
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
     * <p>Switches the current Scene to the mainScene and sets the title of the window to "Welcome "
     * and the username of the current user
     *
     * @since 2019-09-03
     */
    public void showMainScreen() {
        showNode(0, mainParent);
    }

    /**
     * Shows the rulebook screen
     *
     * <p>Switches the main menu Scene to the rulebookScene and sets the title of the window to
     * "Rulebook"
     *
     * @since 2022-11-27
     */
    public void showRulebookScreen() {
        showNode(0, rulebookParent);
    }

    /**
     * Shows the credit screen
     *
     * <p>Switches the main menu Scene to the creditScene and sets the title of the window to
     * "Credits"
     *
     * @since 2022-11-29
     */
    public void showCreditScreen() {
        showNode(0, creditParent);
    }

    /**
     * Shows the setting screen
     *
     * <p>Switches the main menu Scene to the settingParent and sets the title of the window to
     * "Settings"
     *
     * @since 2022-12-11
     */
    public void showSettingScreen() {
        showNode(0, settingParent);
    }

    /**
     * Shows the game screen
     *
     * <p>Switches the current Parent to the gameParent
     *
     * @since 2023-03-09
     */
    public void showGameScreen(Integer lobbyID) {
        showNode(lobbyID, lobbyManagement.getGameParent(lobbyID));
//        showNode(lobbyID, GameManagement.getInstance().getGameParent());
    }

    /**
     * Shows the lobby screen
     *
     * <p>Switches the current Parent to the lobbyParent
     *
     * @since 2023-03-09
     */
    public void showLobbyScreen(Integer lobbyID) {
        showNode(lobbyID, lobbyManagement.getLobbyParent(lobbyID));
    }

    /**
     * Shows the account screen
     *
     * <p>Switches the current Scene to the accountScene and sets the title of the window to
     * "Account options"
     *
     * @since 2022-12-01
     */
    public void showAccountOptionScreen() {
        showNode(0, changeAccountOptionsParent);
    }

    /**
     * Shows the joinOrCreate screen
     *
     * <p>Switches the current Scene to the joinOrCreateScene and sets the title of the window to
     * "Lobbies"
     *
     * @since 2022-11-30
     */
    public void showJoinOrCreateScreen() {
        showNode(0, joinOrCreateParent);
    }

    /**
     * Shows the createLobby screen
     *
     * <p>Switches the current Scene to the createLobbyScene and sets the title of the window to
     * "Create Lobby"
     *
     * @since 2022-11-30
     */
    public void showCreateLobbyScreen() {
        showNode(0, createLobbyParent);
    }

    // -----------------------------------------------------
    // lobby methods
    // -----------------------------------------------------

    /**
     * Helper method to create a tab and initialize the lobby view
     *
     * <p>This method initializes the lobbyview and shows different views depending on if the lobby
     * is set to private or not. Also it opens the setup method to setup the lobby and opens the
     * createTab method to create a tab with the given content.
     *
     * @param lobby the LobbyDTO Object containing all the information of the lobby
     * @param user  the UserDTO Object containing all the information of the User
     * @author Moritz Scheer
     * @see de.uol.swp.common.lobby.dto.LobbyDTO
     * @see de.uol.swp.common.user.UserDTO
     * @since 2023-03-09
     */
    private void createTab(LobbyDTO lobby, UserDTO user) {
        try {
            initLobbyView();

            if (lobby.isMultiplayer()) {
                showJoinOrCreateScreen();
            } else {
                showMainScreen();
            }

            lobbyManagement.setupLobby(lobby, user, lobbyParent);
            tabPresenter.createTab(lobby, lobbyParent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles StartGameMessage detected on the EventBus
     *
     * <p>If a StartGameMessage is detected on the EventBus, this method gets called.
     *
     * @author Moritz Scheer & Maxim Erden
     * @see de.uol.swp.common.game.message.StartGameMessage
     * @since 2023-02-28
     */
    public void createGameView(LobbyDTO lobby, int gameID) {
        try {
            LobbyGame lobbyGameReference = lobbyManagement.setupLobbyGame(lobby.getLobbyID(), gameParent, gameID);
            gameManagement.setupLobbyGame(lobbyGameReference, lobby);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
