package ServerTest.Network;

import Server.Network.Game;
import Server.Network.Hub;
import Server.Network.Lobby;
import Server.Player.AbstractPlayer;
import Server.Player.ComputerPlayer;
import org.junit.Test;
import org.mockito.Mock;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class LobbyTest {
    @Test
    public void addPlayer() throws Exception {
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());

        Lobby lobby = new Lobby(new Hub(), 0);

        Class gameClass = lobby.getClass();
        Field field = gameClass.getDeclaredField("players");
        field.setAccessible(true);
        field.set(lobby, players);

        lobby.addPlayer(new ComputerPlayer());

        Field field2 = gameClass.getDeclaredField("players");
        field2.setAccessible(true);

        assertEquals(field, field2);

    }

    @Test
    public void removePlayers() throws Exception {
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        ComputerPlayer computerPlayer = new ComputerPlayer();
        players.add(computerPlayer);
        players.add(new ComputerPlayer());

        Lobby lobby = new Lobby(new Hub(), 0);

        Class gameClass = lobby.getClass();
        Field field = gameClass.getDeclaredField("players");
        field.setAccessible(true);
        field.set(lobby, players);

        computerPlayer.setPlaying(false);
        lobby.removePlayers();

        Field field2 = gameClass.getDeclaredField("players");
        field2.setAccessible(true);

        assertEquals(field, field2);
    }

    @Test
    public void enter() throws Exception {
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        ComputerPlayer computerPlayer = new ComputerPlayer();
        players.add(computerPlayer);
        players.add(new ComputerPlayer());

        Lobby lobby = new Lobby(new Hub(), 0);

        Class gameClass = lobby.getClass();
        Field field = gameClass.getDeclaredField("players");
        field.setAccessible(true);
        field.set(lobby, players);

        lobby.enter(computerPlayer, 0);

        Field field2 = gameClass.getDeclaredField("players");
        field2.setAccessible(true);

        assertEquals(field, field2);
    }

    @Test
    public void parse() throws Exception {
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        ComputerPlayer computerPlayer = new ComputerPlayer();
        players.add(computerPlayer);

        Lobby lobby = new Lobby(new Hub(), 0);

        lobby.addPlayer(computerPlayer);

        lobby.parse(computerPlayer, "Leave");

        Class gameClass = lobby.getClass();
        Field field = gameClass.getDeclaredField("players");
        field.setAccessible(true);
        field.set(lobby, players);

        assertNotNull(field);
    }

    @Test
    public void reset() throws Exception {
        Lobby lobby = new Lobby(new Hub(), 0);
        lobby.addPlayer(new ComputerPlayer());
        lobby.addPlayer(new ComputerPlayer());
        lobby.addPlayer(new ComputerPlayer());
        lobby.addPlayer(new ComputerPlayer());
        lobby.reset();

        assertFalse(lobby.isFull());
    }

    @Test
    public void sendDetailedGameData() throws Exception {
        Hub mockHub = mock(Hub.class);

        Lobby lobby = new Lobby(mockHub, 0);
        lobby.sendDetailedGameData();

        verify(mockHub).sendGame(lobby);
    }

    @Test
    public void validatePlayerCount() throws Exception {
        Lobby lobby = new Lobby(new Hub(), 0);
        AbstractPlayer abstractPlayer = new ComputerPlayer();
        lobby.addPlayer(abstractPlayer);
        lobby.parse(abstractPlayer, "Settings;Players;2");
        lobby.addPlayer(new ComputerPlayer());

        assertTrue(lobby.validatePlayerCount());
    }

    @Test
    public void validatePlayerReady() throws Exception {
        Lobby lobby = new Lobby(new Hub(), 0);
        AbstractPlayer abstractPlayer = new ComputerPlayer();
        lobby.addPlayer(abstractPlayer);
        abstractPlayer.setReady(true);

        assertFalse(lobby.validatePlayerReady());
    }

    @Test
    public void isFull() throws Exception {
        Lobby lobby = new Lobby(new Hub(), 0);
        AbstractPlayer abstractPlayer = new ComputerPlayer();
        lobby.addPlayer(abstractPlayer);
        lobby.parse(abstractPlayer, "Settings;Players;2");
        lobby.addPlayer(new ComputerPlayer());

        assertFalse(lobby.isFull());
    }

    @Test
    public void getGame() throws Exception {
        Lobby lobby = new Lobby(new Hub(), 0);
        assertNull(lobby.getGame());
    }

    @Test
    public void resetCountdown() throws Exception {
        Lobby lobby = mock(Lobby.class);
        lobby.resetCountdown();

        verify(lobby).resetCountdown();
    }

    @Test
    public void countDown() throws Exception {
        Lobby lobby = mock(Lobby.class);
        lobby.countDown();

        verify(lobby).countDown();
    }

    @Test
    public void getCountDown() throws Exception {
        Lobby lobby = mock(Lobby.class);

        int i = lobby.getCountDown();

        int j = verify(lobby).getCountDown();

        assertEquals(i, j);
    }

    @Test
    public void startGame() throws Exception {
        Lobby lobby = new Lobby(new Hub(), 0);
        AbstractPlayer abstractPlayer = mock(ComputerPlayer.class);

        //when(abstractPlayer.sendMessage());

        lobby.addPlayer(abstractPlayer);
        //lobby.addPlayer(new ComputerPlayer());

        lobby.startGame();
        Game game = lobby.getGame();

        verify(abstractPlayer, times(2)).setNetworkManager(game);
    }

}