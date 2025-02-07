package de.uol.swp.server;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.uol.swp.common.Configuration;
import de.uol.swp.server.chat.TextChatService;
import de.uol.swp.server.communication.ServerHandler;
import de.uol.swp.server.communication.netty.NettyServerHandler;
import de.uol.swp.server.communication.netty.Server;
import de.uol.swp.server.di.ServerModule;
import de.uol.swp.server.gamelogic.GameService;
import de.uol.swp.server.gamelogic.map.MapBuilder;
import de.uol.swp.server.lobby.LobbyManagement;
import de.uol.swp.server.lobby.LobbyService;
import de.uol.swp.server.usermanagement.AuthenticationService;
import de.uol.swp.server.usermanagement.UserManagement;
import de.uol.swp.server.usermanagement.UserService;

import io.netty.channel.ChannelHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class handles the startup of the server, as well as, the creation of default users when the
 * MainMemoryBasedUserStore was still in use.
 *
 * @author Marco Grawunder
 * @see de.uol.swp.server.usermanagement.store.MainMemoryBasedUserStore
 * @since 2017-03-17
 */
class ServerApp {

    private static final Logger LOG = LogManager.getLogger(ServerApp.class);

    /**
     * Main Method
     *
     * <p>This method handles the creation of the server components and the start of the server
     *
     * @param args Any arguments given when starting the application e.g. a port number
     * @since 2017-03-17
     * @see de.uol.swp.server.gamelogic.map.MapBuilder
     * @see de.uol.swp.server.communication.ServerHandler
     * @see de.uol.swp.server.communication.netty.Server
     * @see io.netty.channel.ChannelHandler;
     */
    public static void main(String[] args) throws Exception {
        // Run MapBuilder to avoid NullPointer Exception
        MapBuilder.main(args);
        int port = -1;
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (Exception e) {
                // Ignore and use default value
            }
        }
        if (port < 0) {
            port = Configuration.getDefaultPort();
        }
        LOG.info("Starting Server on port {}", port);

        // create components
        Injector injector = Guice.createInjector(new ServerModule());
        createServices(injector);
        ServerHandler serverHandler = injector.getInstance(ServerHandler.class);
        ChannelHandler channelHandler = new NettyServerHandler(serverHandler);
        Server server = new Server(channelHandler);
        server.start(port);
    }

    /**
     * Helper method to create the services the server uses
     *
     * @param injector the google guice injector used for dependency injection
     * @since 2019-09-18
     * @see com.google.inject.Injector
     */
    private static void createServices(Injector injector) {
        UserManagement userManagement = injector.getInstance(UserManagement.class);

        // Remark: As these services are not referenced by any other class
        // we will need to create instances here (and inject dependencies)
        injector.getInstance(LobbyManagement.class);
        injector.getInstance(UserService.class);
        injector.getInstance(AuthenticationService.class);
        injector.getInstance(LobbyService.class);
        injector.getInstance(TextChatService.class);
        injector.getInstance(GameService.class);
    }
}
