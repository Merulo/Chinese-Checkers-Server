package ServerTest.MapTest;

import Server.Map.Field;
import Server.Player.AbstractPlayer;
import Server.Player.ComputerPlayer;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest {
    @Test
    public void copy() throws Exception {
        Field one = new Field();
        one.setPlayerOnField(new ComputerPlayer());

        Field two = one.copy();

        assertEquals(one.getPlayerOnField(), two.getPlayerOnField());
        assertEquals(one.getHomePlayer(), two.getHomePlayer());

        assertNotEquals(one, two);
    }

    @Test
    public void getPlayerOnField() throws Exception {
        Field one = new Field();
        AbstractPlayer abstractPlayer = new ComputerPlayer();
        one.setPlayerOnField(abstractPlayer);

        assertEquals(abstractPlayer, one.getPlayerOnField());
    }

    @Test
    public void getPartOfMap() throws Exception {
        Field one = new Field();

        assertFalse(one.getPartOfMap());
    }

    @Test
    public void setPlayerOnField() throws Exception {
        Field one = new Field();
        one.setPlayerOnField(new ComputerPlayer());
        assertNotNull(one.getPlayerOnField());
    }

    @Test
    public void getHomePlayer() throws Exception {
        Field one = new Field();
        assertNull(one.getHomePlayer());
    }

}