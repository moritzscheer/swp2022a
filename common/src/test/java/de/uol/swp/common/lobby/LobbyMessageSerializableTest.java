package de.uol.swp.common.lobby;

import de.uol.swp.common.SerializationTestHelper;
import de.uol.swp.common.lobby.message.*;
import de.uol.swp.common.lobby.request.CreateLobbyRequest;
import de.uol.swp.common.lobby.request.LobbyJoinUserRequest;
import de.uol.swp.common.lobby.request.LobbyLeaveUserRequest;
import de.uol.swp.common.user.UserDTO;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LobbyMessageSerializableTest {

    private static final UserDTO defaultUser = new UserDTO("marco", "marco", "marco@grawunder.de");

    @Test
    void testLobbyMessagesSerializable() {
        assertTrue(SerializationTestHelper.checkSerializableAndDeserializable(new CreateLobbyRequest("test", defaultUser, true, "1234"),
                CreateLobbyRequest.class));
        assertTrue(SerializationTestHelper.checkSerializableAndDeserializable(new LobbyJoinUserRequest("test", defaultUser, "1234"),
                LobbyJoinUserRequest.class));
        assertTrue(SerializationTestHelper.checkSerializableAndDeserializable(new LobbyLeaveUserRequest("test", defaultUser, 1, true),
                LobbyLeaveUserRequest.class));
        assertTrue(SerializationTestHelper.checkSerializableAndDeserializable(new UserJoinedLobbyMessage(1, "test", defaultUser),
                UserJoinedLobbyMessage.class));
        assertTrue(SerializationTestHelper.checkSerializableAndDeserializable(new UserLeftLobbyMessage("test", defaultUser, new UserDTO("", "", "")),
                UserLeftLobbyMessage.class));
    }


}
