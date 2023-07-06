package de.uol.swp.server.message;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test GameServiceTest
 *
 * @author Maria Eduarda Costa Leite Andrade
 * @since 2023-07-07
 */
public class ClientDisconnectedMessageTest {

    /**
     * Test GameServiceTest
     *
     * @author Maria Eduarda Costa Leite Andrade
     * @since 2023-07-07
     */
    @Test
    public void testEquals() {
        ClientDisconnectedMessage message1 = new ClientDisconnectedMessage();
        ClientDisconnectedMessage message2 = new ClientDisconnectedMessage();

        assertEquals(message1, message2);
    }
}
