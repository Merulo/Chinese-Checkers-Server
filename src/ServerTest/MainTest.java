package ServerTest;

import static org.junit.Assert.*;
import Server.Main;

public class MainTest {

    @org.junit.Test
    public void addStrings() throws Exception {

        assertEquals("ab", Main.addStrings("a", "b"));

    }

}