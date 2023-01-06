package de.uol.swp.common.lobby.request;

import de.uol.swp.common.message.AbstractRequestMessage;

/**
 * Request for initialising the lobby list in the client
 *
 * This message is sent during the initialization of the lobby list. The server will
 * respond with a AllOnlineLobbiesResponse.
 *
 * @see de.uol.swp.common.user.response.AllOnlineUsersResponse
 * @author Marco Grawunder
 * @since 2019-08-07
 */

public class RetrieveAllOnlineLobbiesRequest extends AbstractRequestMessage {
}
