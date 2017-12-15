package Server.Rules;

import java.util.ArrayList;

abstract public class AbstractMoveRule implements MoveRule{

    private int priority;
    private int max_usages;
    private int uses_left;

    public ArrayList<MapPoint> getNeighbours(){
        return null;
    }

    @Override
    public int getPriority(){
        return priority;
    }

}
