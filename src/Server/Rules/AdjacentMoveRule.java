package Server.Rules;

import java.util.ArrayList;

//allows moving to the adjacent tile if it is free
public class AdjacentMoveRule extends MoveRule {

    public AdjacentMoveRule(){
        priority = 1;
        max_usages = 1;
        uses_left = max_usages;
    }

    public int checkMove(int map[][], ArrayList<MapPoint> mapPoints, int playerID){
        return 1;
    }


}
