package ServerTest.GamePropertiesTest;

import Server.GameProperties.OneTileAnyPawnMoveRule;
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

public class OneTileAnyPawnMoveRuleTest {
    @Test
    public void checkMove() throws Exception {
        Map map = mock(Map.class);
        AbstractPlayer playerToTest = new ComputerPlayer();

        MapPoint starting = new MapPoint(0, 0);
        Field startingField = new Field(true, null);
        startingField.setPlayerOnField(playerToTest);

        MapPoint middle = new MapPoint(1, 0);
        Field middleField = new Field(true, null);
        middleField.setPlayerOnField(playerToTest);

        MapPoint ending = new MapPoint(2, 0);
        Field endingField = new Field(true, null);

        ArrayList<MapPoint> mapPoints = new ArrayList<>();
        mapPoints.add(starting);
        mapPoints.add(middle);
        mapPoints.add(ending);

        when(map.getField(starting)).thenReturn(startingField);
        when(map.getField(middle)).thenReturn(middleField);
        when(map.getField(ending)).thenReturn(endingField);

        OneTileAnyPawnMoveRule rule = new OneTileAnyPawnMoveRule();

        assertEquals(2,rule.checkMove(map, mapPoints, playerToTest, false));
    }

    @Test
    public void getName() throws Exception {
        OneTileAnyPawnMoveRule rule = new OneTileAnyPawnMoveRule();

        assertEquals("Przeskoczenie jednego, dowolnego pionka", rule.getName());
    }

    @Test
    public void makeCopy() throws Exception {
        OneTileAnyPawnMoveRule rule = new OneTileAnyPawnMoveRule();

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

        OneTileAnyPawnMoveRule rule = new OneTileAnyPawnMoveRule();

        assertNull(rule.getBestMove(map, ending, starting, playerToTest));

    }

}