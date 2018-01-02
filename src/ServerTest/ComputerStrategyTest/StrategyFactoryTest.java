package ServerTest.ComputerStrategyTest;

import Server.ComputerStrategy.Strategy;
import Server.ComputerStrategy.StrategyFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class StrategyFactoryTest {
    @Test
    public void getStrategyFactor() throws Exception {
        StrategyFactory insance1 = StrategyFactory.getStrategyFactory();
        StrategyFactory insance2 = StrategyFactory.getStrategyFactory();

        assertTrue(insance1 == insance2);
    }

    @Test
    public void getStrategy() throws Exception {
        StrategyFactory strategyFactory = StrategyFactory.getStrategyFactory();

        Strategy strategy = strategyFactory.getStrategy("some strategy");

        assertNotNull(strategy);
    }

}