package Server.ComputerStrategy;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;
import Server.Rules.MoveDecorator;
import Server.Rules.MoveRule;

import java.util.ArrayList;
import java.util.Comparator;

/**@author Damian Nowak
 * Aggressive strategy focuses on moving the furthest pawn from
 * home. This allows it to excel in the long run.
 */
public class AggressiveStrategy implements Strategy {

    //returns move as String, is not game depended
    public String getMove(MoveDecorator moveDecorator, AbstractPlayer abstractPlayer){
        if(abstractPlayer == null || moveDecorator == null){
            return "Skip;";
        }
        Map map = moveDecorator.getMap();
        //all the home fields
        ArrayList<MapPoint> home = map.getMyHome(abstractPlayer);
        //pawns not in home fields
        ArrayList<MapPoint> notInHome = new ArrayList<>();
        //pawns in home fields
        ArrayList<MapPoint> inHome = new ArrayList<>();
        //moves
        ArrayList<MapPoint> moves;

        //fill pawns in home and not in home
        for(MapPoint mapPoint : map.getMyPawns(abstractPlayer)){
            if(map.getField(mapPoint).getHomePlayer() == abstractPlayer){
                inHome.add(mapPoint);
            }
            else{
                notInHome.add(mapPoint);
            }
        }
        //first home tile w/o this player pawn
        MapPoint freeTile = null;
        //find the tile
        for(MapPoint mapPoint : home){
            if(map.getField(mapPoint).getPlayerOnField() != abstractPlayer){
                freeTile = mapPoint;
                break;
            }
        }

        //should never happen!
        if(freeTile == null){
            return "Skip;";
        }
        //sort the pawns to find the furthest one (aggressive strategy)
        {
            final MapPoint target = freeTile;
            inHome.sort(Comparator.comparing((MapPoint mp) -> mp.getDistance(target)));
            notInHome.sort(Comparator.comparing((MapPoint mp) -> -mp.getDistance(target)));
        }

        //get the pawns not in home which can move closer than the old distance
        for(MapPoint mapPoint : notInHome){
            moves = getBestMove(freeTile, mapPoint, moveDecorator, abstractPlayer);

            if (moves.size() > 0){
                return listToString(moves);
            }
        }
        //get the pawns not in home which can move closer than the old distance
        for(MapPoint mapPoint : inHome){
            moves = getBestMove(freeTile, mapPoint, moveDecorator, abstractPlayer);

            if (moves.size() > 0){
                return listToString(moves);
            }
        }

        return "Skip;";
    }

    //converts list of moves to string
    private String listToString(ArrayList<MapPoint> moves){
        //string builder is more efficient
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Moves;");
        for(MapPoint mapPoint : moves){
            stringBuilder.append(mapPoint.getY()).append(",").append(mapPoint.getX()).append(";");
        }
        return stringBuilder.toString();

    }

    //returns the best move given the current field and target
    //TODO: MAKE THIS A RECURSION!
    private ArrayList<MapPoint> getBestMove(
            MapPoint target,
            MapPoint mapPoint,
            MoveDecorator moveDecorator,
            AbstractPlayer player) {
        //result array
        ArrayList<MapPoint> mapPoints;

        for(MoveRule moveRule : moveDecorator.getMoveRules()) {
            mapPoints = moveRule.getBestMove(moveDecorator.getMap(), target, mapPoint, player);
            if(mapPoints != null){
                return mapPoints;
            }
        }
        return new ArrayList<>();
    }
}
