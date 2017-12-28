package ServerTest.MapTest;

import Server.Map.MapPoint;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapPointTest {
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
    }

}