package Server.Rules;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;

import java.util.*;

public class MoveDecorator {
    private ArrayList<MoveRule> moveRules;
    private int pawnNumber;
    private Map map;

    public MoveDecorator(){
        moveRules = new ArrayList<>();
    }

    public void addRule(MoveRule moveRule){
        moveRules.add(moveRule);
    }

    public ArrayList<MoveRule> getMoveRules() {
        return moveRules;
    }

    public int getPawnNumber(){
        return pawnNumber;
    }

    public void setPawnNumber(int pawnNumber) {
        this.pawnNumber = pawnNumber;
    }

    public void setMap(Map map){
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public void removeRule(MoveRule moveRuleToRemove){
        for(MoveRule moveRule : moveRules){
            if (moveRule.getClass().equals(moveRuleToRemove.getClass())){
                moveRules.remove(moveRule);
                return;
            }
        }
    }

    public void doMove(MapPoint starting, MapPoint ending, AbstractPlayer abstractPlayer){
        map.getField(ending).setPlayerOnField(abstractPlayer);
        map.getField(starting).setPlayerOnField(null);
    }

    public boolean checkMove(ArrayList<MapPoint> mapPoints, AbstractPlayer abstractPlayer){
        sortRules();
        resetRules();
        boolean moveApplied = false;

        if (mapPoints.size() == 0 || mapPoints.size() == 1){
            return false;
        }
        Map copy = map.copy();
        //copy.printMap();

        while (true){
            boolean changed = false;
            for(MoveRule moveRule : moveRules){
                int result = moveRule.checkMove(copy, mapPoints, abstractPlayer, moveApplied);

               // System.out.println("RESULT INT: " + result);

                if( result + 1 == mapPoints.size()){
                    return true;
                }
                if( result != -1){
                    copy.getField(mapPoints.get(0)).setPlayerOnField(null);
                    copy.getField(mapPoints.get(result)).setPlayerOnField(abstractPlayer);
                    //copy.printMap();

                    mapPoints.subList(0, result).clear();
                    changed = true;
                    moveApplied = true;
                    break;
                }
            }
            if (!changed){
                return false;
            }
        }
    }

    public void replacePlayer(AbstractPlayer oldPlayer, AbstractPlayer newPlayer){
        map.replacePlayer(oldPlayer, newPlayer);
    }



    private void resetRules(){
        for(MoveRule moveRule : moveRules){
            moveRule.reset();
        }
    }

    private void sortRules() {
        moveRules.sort(Comparator.comparing(MoveRule::getPriority));
    }




}
