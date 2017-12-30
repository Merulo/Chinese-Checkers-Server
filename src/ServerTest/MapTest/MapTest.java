package ServerTest.MapTest;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;
import Server.Player.ComputerPlayer;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MapTest {
    @Test
    public void copy() throws Exception {
        Map one = new Map(10);
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());

        one.setUpMap(players);

        Map two = one.copy();

        assertNotEquals(one, two);
    }

    @Test
    public void setUpMap() throws Exception {
        Map one = new Map(10);
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());

        one.setUpMap(players);

        assertNotNull(one.getField(new MapPoint(10, 10)));
    }

    @Test
    public void checkWin() throws Exception {
        Map one = new Map(10);
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        AbstractPlayer abstractPlayer = new ComputerPlayer();
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());
        players.add(abstractPlayer);

        one.setUpMap(players);

        assertFalse(one.checkWin(abstractPlayer));
    }

    @Test
    public void getField() throws Exception {
        Map one = new Map(10);
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());

        one.setUpMap(players);

        assertNull(one.getField(new MapPoint(-1, -1)));
    }

    @Test
    public void getMyPawns() throws Exception {
        Map one = new Map(10);
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        AbstractPlayer abstractPlayer = new ComputerPlayer();
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());
        players.add(abstractPlayer);

        one.setUpMap(players);

        assertNotNull(one.getMyPawns(abstractPlayer));
    }

    @Test
    public void getMyHome() throws Exception {
        Map one = new Map(10);
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        AbstractPlayer abstractPlayer = new ComputerPlayer();
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());
        players.add(abstractPlayer);

        one.setUpMap(players);

        assertNotNull(one.getMyHome(abstractPlayer));
    }

    @Test
    public void replacePlayer() throws Exception {
        Map one = new Map(10);
        ArrayList<AbstractPlayer> players = new ArrayList<>();
        AbstractPlayer abstractPlayer = new ComputerPlayer();
        players.add(new ComputerPlayer());
        players.add(abstractPlayer);

        AbstractPlayer abstractPlayer1 = new ComputerPlayer();

        one.setUpMap(players);

        one.replacePlayer(abstractPlayer, abstractPlayer1);

        assertNotNull(one.getMyHome(abstractPlayer1));
        assertNotNull(one.getMyPawns(abstractPlayer1));

        assertEquals(new ArrayList<MapPoint>(), one.getMyHome(abstractPlayer));
        assertEquals(new ArrayList<MapPoint>(), one.getMyPawns(abstractPlayer));
    }

}