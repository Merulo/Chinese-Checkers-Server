package ServerTest.LobbyStateTest;

import Server.LobbyState.*;
import Server.Network.Hub;
import Server.Network.Lobby;
import org.junit.Test;


import static org.junit.Assert.*;


public class PlayingTest {
    @Test
    public void changeStateNext() throws Exception {
        LobbyState lobbyState = new LobbyState(null);
        State state = new Playing();
        state.changeStateNext(lobbyState);

        assertTrue(lobbyState.getState() instanceof Resetting);
    }

    @Test
    public void changeStatePrev() throws Exception {
        LobbyState lobbyState = new LobbyState(new Lobby(null, 0));
        State state = new Playing();
        state.changeStatePrev(lobbyState);

        assertTrue(lobbyState.getState() instanceof Resetting);
    }

    @Test
    public void handleLobby() throws Exception {
        LobbyState lobbyState = new LobbyState(new Lobby(new Hub(), 0));
        State state = new Playing();

        assertNotNull(lobbyState);
        assertNotNull(state);
    }

    @Test
    public void getName() throws Exception {
        State state = new Playing();
        assertEquals("Playing", state.getName());
    }

}