package ServerTest.GamePropertiesTest;

import Server.Network.Hub;
import Server.GameProperties.Settings;
import org.junit.Test;

import static org.junit.Assert.*;

public class SettingsTest {
    @Test
    public void getMoveDecorator() throws Exception {
        Settings settings = new Settings(null, null, "test", 10);

        assertNotNull(settings.getMoveDecorator());
    }

    @Test
    public void getGeneralData() throws Exception {
        Settings settings = new Settings(null, null, "test", 10);

        assertEquals("GameData;10;test;0;4;Ready to start;", settings.getGeneralData(0));
    }

    @Test
    public void getDetailedData() throws Exception {
        Settings settings = new Settings(new Hub(), null, "test", 10);
        String expected = "GameDetailedData;test;0;4;5;Ready to start;RuleOn;RuleOff;0;Ruch na jedno pole obok;1;Przeskoczenie jednego, dowolnego pionka;2;Przeskoczenie dwoch pustych pol;";

        assertEquals(expected, settings.getDetailedData(0));
    }

    @Test
    public void getMaxPlayerNumber() throws Exception {
        Settings settings = new Settings(new Hub(), null, "test", 10);


        assertEquals(4, settings.getMaxPlayerNumber());
    }

    @Test
    public void handleSettingsChange() throws Exception {
        Settings settings = new Settings(new Hub(), null, "test", 10);

        boolean value = settings.handleSettingsChange("Players;6", 0);
        assertFalse(value);
    }

}