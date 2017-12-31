package Server.Rules;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;

import java.util.ArrayList;

/**@author Damian Nowak
 * Abstract MoveRule class
 */
abstract public class MoveRule {

    int priority;
    int max_usages;
    int uses_left;

    public abstract String getName();

    public abstract MoveRule makeCopy();

    //returns -1 if Rule cannot be applied to first and some other point
    //returns n where n is the point that allows to make move according to that rule
    public abstract int checkMove(Map map, ArrayList<MapPoint> mapPoints, AbstractPlayer abstractPlayer, boolean moveApplied);

    //getters, setters
    int getPriority(){
        return -priority;
    }

    void reset(){
        uses_left = max_usages;
    }

    public abstract ArrayList<MapPoint> getBestMove(Map map, MapPoint target, MapPoint starting, AbstractPlayer player);

}
