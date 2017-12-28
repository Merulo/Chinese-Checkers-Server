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
        ArrayList<MapPoint> moves = new ArrayList<>();

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
            System.out.println("SKIP");
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
            int oldDistance = mapPoint.getDistance(freeTile);
            moves.clear();
            moves.add(mapPoint);
            int newDistance = getBestMove(freeTile, mapPoint, moveDecorator, moves, abstractPlayer);
            if (newDistance < oldDistance){
                System.out.println("I GOT A MOVE!");
                for(MapPoint mp : moves){
                    System.out.println("TEST2 " + mp.getX() + " " + mp.getY());
                }
                return listToString(moves);
            }
        }
        System.out.println("NO MOVE");



        return "";
    }

    private String listToString(ArrayList<MapPoint> moves){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Moves;");
        for(MapPoint mapPoint : moves){
            stringBuilder.append(mapPoint.getY()).append(",").append(mapPoint.getX()).append(";");
        }
        return stringBuilder.toString();

    }

    private int getBestMove(
            MapPoint target,
            MapPoint mapPoint,
            MoveDecorator moveDecorator,
            ArrayList<MapPoint> moves,
            AbstractPlayer player){

        MapPoint newPosition = mapPoint;
        ArrayList<MoveRule> moveRules = moveDecorator.getMoveRules();

        System.out.println("Starting from :" + mapPoint.getX() + "," + mapPoint.getY());

        for(MoveRule moveRule : moveRules){
            MapPoint tmp = moveRule.getBestMove(moveDecorator.getMap(), target, mapPoint, player);
            if(newPosition.getDistance(target) > tmp.getDistance(target)){
                System.out.println("Adding :" + tmp.getX() + "," +tmp.getY()+ " ");
                newPosition = target;
                moves.add(tmp);
                for(MapPoint mp : moves){
                    System.out.println("TEST1 " + mp.getX() + " " + mp.getY());
                }
                return newPosition.getDistance(target);
            }
        }
        //reset
        if(newPosition != mapPoint) {
            moves.add(newPosition);
            moveDecorator.checkMove(moves, player);
            return getBestMove(target, newPosition, moveDecorator, moves, player);
        }

        return target.getDistance(newPosition);
    }
}
