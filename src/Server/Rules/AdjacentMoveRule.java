package Server.Rules;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;

import java.util.ArrayList;

//allows moving to the adjacent tile if it is free
public class AdjacentMoveRule extends MoveRule {

    public AdjacentMoveRule(){
        priority = 1;
        max_usages = 1;
        uses_left = max_usages;
    }

    @Override
    public int checkMove(Map map, ArrayList<MapPoint> mapPoints, AbstractPlayer abstractPlayer, boolean moveApplied){
        //not enough points
        if(mapPoints.size() < 2) {
            return -1;
        }
        //no more uses
        if(uses_left == 0) {
            return -1;
        }
        //this move cannot be combined with others
        if(moveApplied){
            return -1;
        }

        MapPoint starting   = mapPoints.get(0);
        MapPoint ending     = mapPoints.get(1);

        //field is taken
        if(map.getField(ending).getPlayerOnField() != null){
            return -1;
        }
        //field is not part of map
        if(!map.getField(ending).getPartOfMap()){
            return -1;
        }

        if(map.getField(starting).getPlayerOnField() != abstractPlayer){
            return -1;
        }
        //distance is not 1
        if (starting.getDistance(ending) != 1) {
            return -1;
        }

        uses_left--;

        return 1;
    }

    @Override
    public String getName() {
        return "Ruch na jedno pole obok";
    }

    @Override
    public MoveRule makeCopy(){
        return new AdjacentMoveRule();
    }

    @Override
    public ArrayList<MapPoint> getBestMove(Map map, MapPoint target, MapPoint starting, AbstractPlayer player){
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
                        return move;
                    }
                }
            }
        }
        return null;
    }
}
