package ServerTest.GamePropertiesTest;

import Server.GameProperties.TwoTilesNoPawnsRule;
import Server.Map.Field;
import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;
import Server.Player.ComputerPlayer;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TwoTilesNoPawnsRuleTest {
    @Test
    public void checkMove() throws Exception {
        Map map = mock(Map.class);
        AbstractPlayer playerToTest = new ComputerPlayer();

        MapPoint starting = new MapPoint(0, 0);
        Field startingField = new Field(true, null);
        startingField.setPlayerOnField(playerToTest);

        MapPoint middle1 = new MapPoint(1, 0);
        Field middleField1 = new Field(true, null);

        MapPoint middle2 = new MapPoint(2, 0);
        Field middleField2 = new Field(true, null);

        MapPoint ending = new MapPoint(3, 0);
        Field endingField = new Field(true, null);

        ArrayList<MapPoint> mapPoints = new ArrayList<>();
        mapPoints.add(starting);
        mapPoints.add(middle1);
        mapPoints.add(middle2);
        mapPoints.add(ending);

        when(map.getField(starting)).thenReturn(startingField);
        when(map.getField(middle1)).thenReturn(middleField1);
        when(map.getField(middle2)).thenReturn(middleField2);
        when(map.getField(ending)).thenReturn(endingField);

        TwoTilesNoPawnsRule rule = new TwoTilesNoPawnsRule();

        assertEquals(3,rule.checkMove(map, mapPoints, playerToTest, false));
    }

    @Test
    public void getName() throws Exception {
        TwoTilesNoPawnsRule rule = new TwoTilesNoPawnsRule();

        assertEquals("Przeskoczenie dwoch pustych pol", rule.getName());
    }

    @Test
    public void makeCopy() throws Exception {
        TwoTilesNoPawnsRule rule = new TwoTilesNoPawnsRule();

        assertEquals(rule.getClass(), rule.makeCopy().getClass());
        assertNotEquals(rule, rule.makeCopy());

    }

    @Test
    public void getBestMove() throws Exception {
        Map map = mock(Map.class);
        AbstractPlayer playerToTest = new ComputerPlayer();

        MapPoint starting = new MapPoint(1, 1);
        Field startingField = new Field(true, null);
        startingField.setPlayerOnField(playerToTest);

        MapPoint ending = new MapPoint(2, 0);
        Field endingField = new Field(true, playerToTest);

        when(map.getField(starting)).thenReturn(startingField);
        when(map.getField(ending)).thenReturn(endingField);

        TwoTilesNoPawnsRule rule = new TwoTilesNoPawnsRule();

        assertNull(rule.getBestMove(map, ending, starting, playerToTest));

    }

}