package ServerTest.RulesTest;

import Server.Map.Map;
import Server.Rules.AdjacentMoveRule;
import Server.Rules.MoveDecorator;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MoveDecoratorTest {

    @Test
    public void addRule() throws Exception {
        MoveDecorator moveDecorator = new MoveDecorator();
        moveDecorator.addRule(new AdjacentMoveRule());

        assertEquals(1, moveDecorator.getMoveRules().size());

    }

    @Test
    public void getMoveRules() throws Exception {
        MoveDecorator moveDecorator = new MoveDecorator();

        assertNotNull(moveDecorator.getMoveRules());
    }

    @Test
    public void getPawnNumber() throws Exception {
        MoveDecorator moveDecorator = new MoveDecorator();
        moveDecorator.setPawnNumber(10);

        assertEquals(10, moveDecorator.getPawnNumber());
    }

    @Test
    public void setMap() throws Exception {
        MoveDecorator moveDecorator = new MoveDecorator();
        moveDecorator.setPawnNumber(10);
        moveDecorator.setMap(new Map(10));

        assertNotNull(moveDecorator.getMap());
    }

    @Test
    public void getMap() throws Exception {
        MoveDecorator moveDecorator = new MoveDecorator();
        moveDecorator.setPawnNumber(10);
        moveDecorator.setMap(new Map(10));

        assertNotNull(moveDecorator.getMap());
    }

    @Test
    public void removeRule() throws Exception {
        MoveDecorator moveDecorator = new MoveDecorator();
        moveDecorator.addRule(new AdjacentMoveRule());
        moveDecorator.removeRule(new AdjacentMoveRule());

        assertEquals(0, moveDecorator.getMoveRules().size());
    }

    @Test
    public void checkMove() throws Exception {
        MoveDecorator moveDecorator = new MoveDecorator();
        moveDecorator.addRule(new AdjacentMoveRule());

        assertFalse(moveDecorator.checkMove(new ArrayList<>(),null));
    }

}