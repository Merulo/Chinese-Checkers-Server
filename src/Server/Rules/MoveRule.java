package Server.Rules;

import Server.Map.Field;
import Server.Map.Map;
import Server.Map.MapPoint;

import java.util.ArrayList;

abstract public class MoveRule {

    int priority;
    int max_usages;
    int uses_left;

    //TODO: RETHINK THIS IDEA
    public ArrayList<MapPoint> getNeighbours(){
        return null;
    }

    public int getPriority(){
        return priority;
    }

    public boolean canUse(){
        return (uses_left>0);
    }

    //returns -1 if Rule cannot be applied to first and some other point
    //returns n where n is the point that allows to make move according to that rule
    public abstract int checkMove(Map map, ArrayList<MapPoint> mapPoints, int playerID);

    void reset(){
        uses_left = max_usages;
    }

    public abstract String getName();

}
