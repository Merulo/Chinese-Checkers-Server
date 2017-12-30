package ServerTest.LobbyStateTest;

import Server.LobbyState.LobbyState;
import Server.LobbyState.Open;
import Server.Network.Hub;
import Server.Network.Lobby;
import org.junit.Test;

import static org.junit.Assert.*;

public class LobbyStateTest {
    @Test
    public void handleLobby() throws Exception {
        LobbyState lobbyState = new LobbyState(new Lobby(new Hub(), 0));
        lobbyState.handleLobby();

        assertTrue(lobbyState.getState() instanceof Open);
    }

    @Test
    public void getLobby() throws Exception {
        LobbyState lobbyState = new LobbyState(new Lobby(new Hub(), 0));
        assertNotNull(lobbyState.getLobby());
    }

}