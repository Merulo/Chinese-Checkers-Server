package Server.Rules;

import java.util.ArrayList;
//TODO: CHECK IF THIS CAN BE MOVED TO ABSTARCT MOVE RULE
public interface MoveRule {

    //returns -1 if Rule cannot be applied to first and some other point
    //returns n where n is the point that allows to make move according to that rule
    int checkMove(int map[][], ArrayList<MapPoint> mapPoints, int playerID);

    void reset();

    int getPriority();

}
