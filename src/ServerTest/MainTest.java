package ServerTest;

import Server.Main;

import static org.junit.Assert.*;

public class MainTest {

    @org.junit.Test
    public void addStrings() throws Exception {

        assertEquals("ab", Main.addStrings("a", "b"));

    }

    @org.junit.Test
    public void addThreeStrings() throws Exception {

        assertEquals("abc", Main.addThreeStrings("a", "b", "c"));

    }
}