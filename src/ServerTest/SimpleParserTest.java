package ServerTest;

import Server.SimpleParser;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleParserTest {
    @Test
    public void parse() throws Exception {
        String message = "ThisIsTestMessage;ThisIsMore";
        assertEquals("ThisIsTestMessage", SimpleParser.parse(message));
    }

    @Test
    public void parse1() throws Exception {
        String message = "ThisIsTestMessageXThisIsMore";
        assertEquals("ThisIsTestMessage", SimpleParser.parse(message,"X"));
    }

    @Test
    public void cut() throws Exception {
        String message = "ThisIsTestMessage;ThisIsMore";
        assertEquals("ThisIsMore", SimpleParser.cut(message));
    }

    @Test
    public void cut1() throws Exception {
        String message = "ThisIsTestMessageXThisIsMore";
        assertEquals("ThisIsMore", SimpleParser.cut(message,"X"));
    }

}