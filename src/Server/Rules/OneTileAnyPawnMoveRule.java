package Server.Rules;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;

import java.util.ArrayList;

//allows moving to the adjacent tile if it is free
public class OneTileAnyPawnMoveRule extends MoveRule {

    public OneTileAnyPawnMoveRule(){
        priority = 10;
        max_usages = 1;
        uses_left = max_usages;
    }

    public int checkMove(Map map, ArrayList<MapPoint> mapPoints, AbstractPlayer abstractPlayer, boolean moveApplied){
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
        if(!starting.areAligned(ending)){
            return  -1;
        }

        if(map.getField(starting).getPlayerOnField() != abstractPlayer){
            return -1;
        }

        if (starting.getDistance(ending) != 2) {

            return -1;
        }
        uses_left--;
        map.getField(ending).setPlayerOnField(abstractPlayer);
        map.getField(starting).setPlayerOnField(null);
        return 2;
    }

    @Override
    public String getName() {
        return "Przeskoczenie jednego, dowolnego pionka";
    }

    @Override
    public MoveRule makeCopy(){
        return new OneTileAnyPawnMoveRule();
    }

    @Override
    public MapPoint getBestMove(Map map, MapPoint target, MapPoint starting, AbstractPlayer player){
        MapPoint best = starting;
        int distance = target.getDistance(starting);
        ArrayList<MapPoint> move = new ArrayList<>();

        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if(map.getField(new MapPoint(starting.getX() + i, starting.getY() + j))!= null){
                    MapPoint mp = new MapPoint(starting.getX() + i, starting.getY() + j);
                    move.clear();
                    reset();
                    move.add(starting);
                    move.add(mp);

                    if(checkMove(map, move, player,false) == -1){
                        continue;
                    }

                    if (target.getDistance(mp) < distance){
                        distance = target.getDistance(mp);
                        best = mp;
                    }
                }
            }
        }
        return best;
    }
}

