package Server.Rules;

import Server.Map.Map;
import Server.Map.MapPoint;

import java.util.ArrayList;

abstract public class MoveRule {

    int priority;
    int max_usages;
    int uses_left;

    public abstract String getName();

    public abstract MoveRule makeCopy();

    //returns -1 if Rule cannot be applied to first and some other point
    //returns n where n is the point that allows to make move according to that rule
    public abstract int checkMove(Map map, ArrayList<MapPoint> mapPoints, int playerID);

    //getters, setters
    public int getPriority(){
        return priority;
    }

    public boolean canUse(){
        return (uses_left>0);
    }
    void reset(){
        uses_left = max_usages;
    }







    //TODO: RETHINK WHAT IS BENEATH
    boolean applied = false;
    public ArrayList<MapPoint> getNeighbours(){
        return null;
    }

    public boolean getApplied(){
        return applied;
    }

    public void setApplied(boolean value){
        applied = value;
    }

}
