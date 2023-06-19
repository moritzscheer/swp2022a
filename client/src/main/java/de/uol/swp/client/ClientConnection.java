package de.uol.swp.client;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import de.uol.swp.common.MyObjectDecoder;
import de.uol.swp.common.MyObjectEncoder;
import de.uol.swp.common.message.*;
import de.uol.swp.common.user.exception.ServerNotRespondingExceptionMessage;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.InetSocketAddress;
import java.nio.channels.NotYetConnectedException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * The Helper class
 *
 * <p>This Class is used to handle the case that the server does not respond. It times out after an
 * interval specified when it is called.
 *
 * @author Ole Zimmermann
 * @since 2023-06-18
 */
class Helper extends TimerTask {
    EventBus eventBus;

    public void setBus(EventBus bus) {
        this.eventBus = bus;
    }

    public void run() {
        eventBus.post(
                new ServerNotRespondingExceptionMessage(
                        "unknown reason! \n Please restart the client and/or the server!"));
    }
}

/**
 * The ClientConnection Connection class
 *
 * <p>This Class manages connecting to a server, disconnecting from the server and handling of
 * incoming and outgoing messages.
 *
 * @author Marco Grawunder
 * @since 2017-03-17
 */
@SuppressWarnings("UnstableApiUsage")
public class ClientConnection {

    private static final Logger LOG = LogManager.getLogger(ClientConnection.class);

    private final String host;
    private final int port;
    private final List<ConnectionListener> connectionListener = new CopyOnWriteArrayList<>();
    private EventLoopGroup group;
    private EventBus eventBus;
    private Channel channel;

    protected Timer timer;

    /**
     * Creates a new connection to a specific port on the given host
     *
     * @param host The server name or IP to connect to
     * @param port The server port to connect to
     * @since 2017-03-17
     */
    @Inject
    public ClientConnection(@Assisted String host, @Assisted int port, EventBus eventBus) {
        this.host = host;
        this.port = port;
        setEventBus(eventBus);
    }

    /**
     * Sets the EventBus for the object
     *
     * <p>Sets the EventBus for the object and registers the object to it.
     *
     * @implNote If the object already has an EventBus it is replaced but not unregistered
     * @param eventBus The new EventBus to set
     * @since 2019-09-18
     */
    public void setEventBus(EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(this);
    }

    /**
     * The netty init method
     *
     * <p>The example method on how to initialize a connection to a server via netty. Inside the
     * ChannelInitializer multiple settings are made with the {@code pipeline.addLast()} method.
     * Things usually added are encoders, decoders and the ChannelHandler.
     *
     * @implNote If no ChannelHandler is added, communication will not be possible
     * @throws InterruptedException Connection failed
     * @since 2017-03-17
     */
    public void start() throws InterruptedException {
        group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host, port))
                    .handler(
                            new ChannelInitializer<SocketChannel>() {

                                @Override
                                protected void initChannel(SocketChannel ch) {
                                    // Add both Encoder and Decoder to send and receive serializable
                                    // objects
                                    ch.pipeline().addLast(new MyObjectEncoder());
                                    ch.pipeline()
                                            .addLast(
                                                    new MyObjectDecoder(
                                                            ClassResolvers.cacheDisabled(null)));
                                    // Add a client handler
                                    ch.pipeline().addLast(new ClientHandler(ClientConnection.this));
                                }
                            });
            ChannelFuture f = b.connect().sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully().sync();
        }
    }

    /**
     * Disconnects the client from the server
     *
     * <p>Disconnects the client from the server and prints the stack trace if an
     * InterruptedException is thrown.
     *
     * @since 2017-03-17
     */
    public void close() throws InterruptedException {
        group.shutdownGracefully().sync();
    }

    /**
     * Stops the timer
     *
     * <p>Stops the timer and purges it. This is necessary to prevent the timer from running in the
     * background when the client is supposed to be closed.
     *
     * @author Ole Zimmermann
     * @since 2023-06-18
     */
    public void stopTimer() {
        try {
            this.timer.cancel();
            this.timer.purge();
        } catch (Exception e) {
            LOG.error("Error while stopping timer");
        }
    }

    /**
     * Calls the ConnectionEstablished method of every ConnectionListener added to this.
     *
     * @param channel The netty channel the new Connection is established on
     * @see de.uol.swp.client.ConnectionListener
     * @since 2017-03-17
     */
    void fireConnectionEstablished(Channel channel) {
        for (ConnectionListener listener : connectionListener) {
            listener.connectionEstablished(channel);
        }
        this.channel = channel;
    }

    /**
     * Add a new ConnectionListener to the ConnectionListener Array of this Object
     *
     * @param listener The ConnectionListener to add to the Array
     * @see de.uol.swp.client.ConnectionListener
     * @since 2017-03-17
     */
    public void addConnectionListener(ConnectionListener listener) {
        this.connectionListener.add(listener);
    }

    /**
     * Processes the incoming messages
     *
     * <p>This method posts the message it gets on the EventBus
     *
     * <p>Post on event bus " and the Message to the LOG if the LOG-Level is set to DEBUG or higher.
     * If it is a different kind of Message, it gets discarded and with LOG-Level set to WARN or
     * higher "Can only process ServerMessage and ResponseMessage. Received " and the message are
     * written to the LOG. Every time it receives a message it cancels the timer and purges it since
     * the server just responded.
     *
     * @param in The incoming messages read by the ClientHandler
     * @see de.uol.swp.client.ClientHandler
     * @since 2017-03-17
     */
    public void receivedMessage(Message in) {
        LOG.debug("Received message. Post on event bus {}", in);
        eventBus.post(in);
        try {
            this.timer.cancel();
            this.timer.purge();
        } catch (Exception e) {
            LOG.error("Timer cancel failed");
        }
    }

    /**
     * Handles RequestMessages detected on the EventBus
     *
     * <p>If the client is connected to the server and the channel of this object is set the
     * RequestMessage given to this method is send to the server. Otherwise "Some tries to send a
     * message, but server is not connected" is written to the LOG if the LOG-Level is set to WARN
     * or higher. Every time it sends a message it cancels the timer and purges it. After that it
     * creates a new task that waits 5 seconds for a response and then executes the Helper task.
     *
     * @param message The RequestMessage object to send to the server
     * @since 2019-08-29
     * @see de.uol.swp.client.Helper
     */
    @Subscribe
    public void onRequestMessage(RequestMessage message) {
        if (channel != null) {
            channel.writeAndFlush(message);
            Helper task = new Helper();
            task.setBus(eventBus);
            try {
                this.timer.cancel();
                this.timer.purge();
            } catch (Exception e) {
            }
            this.timer = new Timer();
            this.timer.schedule(task, 5000);
        } else {
            throw new NotYetConnectedException();
        }
    }

    /**
     * Handles ExceptionMessages found on the EventBus
     *
     * <p>If an ExceptionMessage object is detected on the EventBus, this method is called. It calls
     * the exceptionOccurred method of every ConnectionListener in the ConnectionListener array.
     *
     * @param message The ExceptionMessage object found on the EventBus
     * @since 2017-03-17
     */
    @Subscribe
    public void onExceptionMessage(ExceptionMessage message) {
        for (ConnectionListener l : connectionListener) {
            l.exceptionOccurred(message.getException());
        }
    }

    /**
     * Handles errors produced by the EventBus
     *
     * <p>If an DeadEvent object is detected on the EventBus, this method is called. It writes
     * "DeadEvent detected " and the error message of the detected DeadEvent object to the log, if
     * the loglevel is set to WARN or higher.
     *
     * @param deadEvent The DeadEvent object found on the EventBus
     * @since 2017-03-17
     */
    @Subscribe
    public void onDeadEvent(DeadEvent deadEvent) {
        LOG.warn("DeadEvent detected {}", deadEvent);
    }

    /**
     * Handles the distribution of throwable messages
     *
     * <p>This method distributes throwable messages to the ConnectionListeners. It calls the
     * exceptionOccurred method of every ConnectionListener in the ConnectionListener array passing
     * them the message.
     *
     * @param message The ExceptionMessage object found on the EventBus
     * @see de.uol.swp.client.ClientHandler
     * @since 2017-03-17
     */
    public void process(Throwable message) {
        for (ConnectionListener l : connectionListener) {
            l.exceptionOccurred(message.getMessage());
        }
    }
}
