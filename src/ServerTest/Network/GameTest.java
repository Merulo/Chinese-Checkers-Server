package ServerTest.Network;

import Server.Network.Game;
import Server.Network.Hub;
import Server.Network.Lobby;
import Server.Player.AbstractPlayer;
import Server.Player.ComputerPlayer;
import Server.Rules.MoveDecorator;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {
    @Test
    public void addPlayer() throws Exception {
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());

        Game game = new Game(players, new Hub(), new Lobby(new Hub(), 1), new MoveDecorator());

        Class gameClass = game.getClass();
        Field field = gameClass.getDeclaredField("players");
        field.setAccessible(true);
        field.set(game, players);

        game.addPlayer(new ComputerPlayer());

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

        Game game = new Game(players, new Hub(), new Lobby(new Hub(), 1), new MoveDecorator());

        Class gameClass = game.getClass();
        Field field = gameClass.getDeclaredField("players");
        field.setAccessible(true);
        field.set(game, players);

        computerPlayer.setPlaying(false);
        game.removePlayers();

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

        Game game = new Game(players, new Hub(), new Lobby(new Hub(), 1), new MoveDecorator());

        Class gameClass = game.getClass();
        Field field = gameClass.getDeclaredField("players");
        field.setAccessible(true);
        field.set(game, players);

        game.enter(computerPlayer, 0);

        Field field2 = gameClass.getDeclaredField("players");
        field2.setAccessible(true);

        assertEquals(field, field2);
    }

    @Test
    public void parse() throws Exception {
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        ComputerPlayer computerPlayer = new ComputerPlayer();
        players.add(computerPlayer);

        Game game = new Game(players, new Hub(), new Lobby(new Hub(), 1), new MoveDecorator());

        game.parse(computerPlayer, "Leave");

        assertTrue(game.areThereOnlyBotsInGame());
    }

    @Test
    public void areThereOnlyBotsInGame() throws Exception {
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        ComputerPlayer computerPlayer = new ComputerPlayer();
        players.add(computerPlayer);

        Game game = new Game(players, new Hub(), new Lobby(new Hub(), 1), new MoveDecorator());

        assertTrue(game.areThereOnlyBotsInGame());
    }

}