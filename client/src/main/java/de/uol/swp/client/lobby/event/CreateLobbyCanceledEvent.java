package de.uol.swp.client.lobby.event;

/**
 * Event used to show the JoinOrCreate window if the back button was pressed in the CreateLobby Window
 *
 * In order to show the JoinOrCreate window using this event, post an instance of it
 * onto the eventBus the SceneManager is subscribed to.
 *
 * @author Moritz Scheer
 * @see de.uol.swp.client.SceneManager
 * @since 2022-18-12
 */
public class CreateLobbyCanceledEvent {
}
