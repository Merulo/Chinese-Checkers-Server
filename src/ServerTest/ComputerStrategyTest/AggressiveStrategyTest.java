package ServerTest.ComputerStrategyTest;

import Server.ComputerStrategy.AggressiveStrategy;
import org.junit.Test;

import static org.junit.Assert.*;

public class AggressiveStrategyTest {
    @Test
    public void getMove() throws Exception {
        AggressiveStrategy aggressiveStrategy = new AggressiveStrategy();

        assertEquals("Skip;", aggressiveStrategy.getMove(null, null));
    }

}