package Server.Rules;

import Server.Map.Field;
import Server.Map.Map;
import Server.Map.MapPoint;

import java.util.ArrayList;

//allows moving to the adjacent tile if it is free
public class AdjacentMoveRule extends MoveRule {

    public AdjacentMoveRule(){
        priority = 1;
        max_usages = 1;
        uses_left = max_usages;
    }

    public int checkMove(Map map, ArrayList<MapPoint> mapPoints, int playerID){
        if(mapPoints.size() < 2) {
            return -1;
        }

        if(uses_left == 0){
            return -1;
        }

        MapPoint starting   = mapPoints.get(0);
        MapPoint ending     = mapPoints.get(1);

        if(map.getField(ending).getPlayerOnField() != null){
            return -1;
        }
        if(!map.getField(ending).getPartOfMap()){
            return -1;
        }

        if (starting.getDistance(ending) == 1) {
            uses_left--;
            return 1;
        }
        return -1;
    }

    @Override
    public String getName() {
        return "Ruch na jedno pole obok";
    }
}
