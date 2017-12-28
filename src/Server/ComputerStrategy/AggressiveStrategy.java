package Server.ComputerStrategy;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;
import Server.Rules.MoveDecorator;
import Server.Rules.MoveRule;

import java.util.ArrayList;
import java.util.Comparator;

public class AggressiveStrategy implements Strategy {
    public String getMove(MoveDecorator moveDecorator, AbstractPlayer abstractPlayer){
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
        //sort the pawns to find the closes one (aggressive strategy)
        {
            final MapPoint target = freeTile;
            inHome.sort(Comparator.comparing((MapPoint mp) -> mp.getDistance(target)));
            notInHome.sort(Comparator.comparing((MapPoint mp) -> mp.getDistance(target)));
        }

        //get the pawns not in home which can move closer than the old distance
        for(MapPoint mapPoint : notInHome){
            moves = getBestMove(freeTile, mapPoint, moveDecorator, abstractPlayer);

            if (moves.size() > 0){
                return listToString(moves);
            }
        }
        return "Skip;";
    }

    private String listToString(ArrayList<MapPoint> moves){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Moves;");
        for(MapPoint mapPoint : moves){
            stringBuilder.append(mapPoint.getY()).append(",").append(mapPoint.getX()).append(";");
        }
        return stringBuilder.toString();

    }

    private ArrayList<MapPoint> getBestMove(
            MapPoint target,
            MapPoint mapPoint,
            MoveDecorator moveDecorator,
            AbstractPlayer player) {

        ArrayList<MoveRule> moveRules = moveDecorator.getMoveRules();
        ArrayList<MapPoint> mapPoints = new ArrayList<>();

        for(MoveRule moveRule : moveRules) {
            mapPoints = moveRule.getBestMove(moveDecorator.getMap(), target, mapPoint, player);
        }
        if(mapPoints == null){
            mapPoints = new ArrayList<>();
        }
        return mapPoints;
    }
}
