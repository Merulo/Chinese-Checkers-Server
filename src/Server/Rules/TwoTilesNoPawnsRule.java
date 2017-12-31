package Server.Rules;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;

import java.util.ArrayList;

/**@author Damian Nowak
 * Allows jumping two free tiles
 */
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
        ArrayList<MapPoint> moves;

        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                moves = testThisConfiguration(i, j, map, target, starting, player);
                if(moves != null){
                    return moves;
                }
            }
        }

        return null;
    }

    private ArrayList<MapPoint> testThisConfiguration(int dx, int dy, Map map, MapPoint target, MapPoint starting, AbstractPlayer player){
        //this is way faster than for's
        //for's have to check 49 cases, here we have 6
        ArrayList<MapPoint> move = new ArrayList<>();
        int distance = target.getDistance(starting);

        move.clear();
        move.add(starting);
        move.add(new MapPoint(starting.getX() - dx   , starting.getY() - dy));
        move.add(new MapPoint(starting.getX() -(dx*2), starting.getY() -(dy * 2)));
        move.add(new MapPoint(starting.getX() -(dx*3), starting.getY() -(dy * 3)));

        if(testIfFieldsNotNull(move, map)){
            reset();
            if(checkMove(map, move, player,false) != -1){
                if (target.getDistance(move.get(move.size() -1)) < distance){
                    return move;
                }
            }
        }
        return null;
    }

    private boolean testIfFieldsNotNull(ArrayList<MapPoint> mapPoints, Map map){
        for(MapPoint mapPoint : mapPoints){
            if(map.getField(mapPoint) == null){
                return false;
            }
        }

        return true;
    }
}

