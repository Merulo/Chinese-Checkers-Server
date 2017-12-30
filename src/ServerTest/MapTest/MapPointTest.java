package ServerTest.MapTest;

import Server.Map.MapPoint;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapPointTest {
    @Test
    public void copy() throws Exception {
        MapPoint one = new MapPoint(10, 10);
        MapPoint two = one.copy();

        assertEquals(one.getX(), two.getX());
        assertEquals(one.getY(), two.getY());

        assertNotEquals(one, two);
    }

    @Test
    public void getX() throws Exception {
        MapPoint one = new MapPoint(10, 10);
        assertEquals(10, one.getX());
    }

    @Test
    public void getY() throws Exception {
        MapPoint one = new MapPoint(10, 10);
        assertEquals(10, one.getY());
    }

    @Test
    public void getDistance() throws Exception {
        MapPoint mp = new MapPoint(0 , 3);
        MapPoint mp2 = new MapPoint(2,3);
        MapPoint mp3 = new MapPoint(1, 3);

        assertEquals(2, mp.getDistance(mp2));
        assertEquals(1, mp.getDistance(mp3));
    }

    @Test
    public void areAligned() throws Exception {
        MapPoint mp = new MapPoint(0 , 3);
        MapPoint mp2 = new MapPoint(2,4);
        MapPoint mp3 = new MapPoint(10, 3);

        assertTrue(mp.areAligned(mp3));
        assertFalse(mp2.areAligned(mp));
        assertFalse(mp2.areAligned(mp3));

    }

}