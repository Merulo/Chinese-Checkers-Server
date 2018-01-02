package ServerTest.GamePropertiesTest;

import Server.GameProperties.AdjacentMoveRule;
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

public class AdjacentMoveRuleTest {
    @Test
    public void checkMove() throws Exception {
        Map map = mock(Map.class);
        AbstractPlayer playerToTest = new ComputerPlayer();

        MapPoint starting = new MapPoint(0, 0);
        Field startingField = new Field(true, null);
        startingField.setPlayerOnField(playerToTest);

        MapPoint ending = new MapPoint(1, 0);
        Field endingField = new Field(true, null);

        ArrayList<MapPoint> mapPoints = new ArrayList<>();
        mapPoints.add(starting);
        mapPoints.add(ending);

        when(map.getField(starting)).thenReturn(startingField);
        when(map.getField(ending)).thenReturn(endingField);

        AdjacentMoveRule adjacentMoveRule = new AdjacentMoveRule();

        assertEquals(1,adjacentMoveRule.checkMove(map, mapPoints, playerToTest, false));
    }

    @Test
    public void getName() throws Exception {
        AdjacentMoveRule adjacentMoveRule = new AdjacentMoveRule();

        assertEquals("Ruch na jedno pole obok", adjacentMoveRule.getName());
    }

    @Test
    public void makeCopy() throws Exception {
        AdjacentMoveRule adjacentMoveRule = new AdjacentMoveRule();

        assertEquals(adjacentMoveRule.getClass(), adjacentMoveRule.makeCopy().getClass());
        assertNotEquals(adjacentMoveRule, adjacentMoveRule.makeCopy());

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

        AdjacentMoveRule adjacentMoveRule = new AdjacentMoveRule();

        assertNull(adjacentMoveRule.getBestMove(map, ending, starting, playerToTest));

    }

}