package Server.Rules;

import Server.Map.Map;
import Server.Map.MapPoint;
import java.util.ArrayList;

//allows moving to the adjacent tile if it is free
public class OneTileAnyPawnMoveRule extends MoveRule {

    public OneTileAnyPawnMoveRule(){
        priority = 10;
        max_usages = 1;
        uses_left = max_usages;
    }

    public int checkMove(Map map, ArrayList<MapPoint> mapPoints, int playerID){
        //not enough points
        if(mapPoints.size() < 3) {
            return -1;
        }

        MapPoint starting   = mapPoints.get(0);
        MapPoint middle     = mapPoints.get(1);
        MapPoint ending     = mapPoints.get(2);

        //field is taken
        if(map.getField(ending).getPlayerOnField() != null){
            return -1;
        }
        //field is not part of map
        if(!map.getField(ending).getPartOfMap()){
            return -1;
        }
        if(map.getField(middle).getPlayerOnField() == null){
            return -1;
        }
        if(!starting.areAlined(ending)){
            return  -1;
        }
        if (starting.getDistance(ending) == 2) {
            uses_left--;
            return 2;
        }
        return -1;
    }

    @Override
    public String getName() {
        return "Przeskoczenie jednego, dowolnego pionka";
    }
}

