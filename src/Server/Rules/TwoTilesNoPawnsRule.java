package Server.Rules;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;

import java.util.ArrayList;

//allows moving to the adjacent tile if it is free
public class TwoTilesNoPawnsRule extends MoveRule {

    public TwoTilesNoPawnsRule(){
        priority = 10;
        max_usages = 1;
        uses_left = max_usages;
    }

    public int checkMove(Map map, ArrayList<MapPoint> mapPoints, AbstractPlayer abstractPlayer, boolean moveApplied){
        //not enough points
        if(mapPoints.size() < 4) {
            return -1;
        }

        MapPoint starting   = mapPoints.get(0);
        MapPoint middle1    = mapPoints.get(1);
        MapPoint middle2    = mapPoints.get(2);
        MapPoint ending     = mapPoints.get(3);

        //field is taken
        if(map.getField(ending).getPlayerOnField() != null){
            return -1;
        }
        //field is not part of map
        if(!map.getField(ending).getPartOfMap()){
            return -1;
        }
        if(map.getField(middle1).getPlayerOnField() != null){
            return -1;
        }
        if(map.getField(middle2).getPlayerOnField() != null){
            return -1;
        }
        if(!starting.areAligned(ending)){
            return  -1;
        }
        if (starting.getDistance(ending) != 3) {
            return -1;
        }
        if(map.getField(starting).getPlayerOnField() != abstractPlayer){
            return -1;
        }

        uses_left--;
        return 3;
    }

    @Override
    public String getName() {
        return "Przeskoczenie dwoch pustych pol";
    }

    @Override
    public MoveRule makeCopy(){
        return new TwoTilesNoPawnsRule();
    }

    @Override
    public ArrayList<MapPoint> getBestMove(Map map, MapPoint target, MapPoint starting, AbstractPlayer player){
        //TODO: IMPLEMENT THIS METHOD!
        return null;
    }
}

