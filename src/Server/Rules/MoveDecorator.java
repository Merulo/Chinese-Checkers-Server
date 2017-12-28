package Server.Rules;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;

import java.util.*;

public class MoveDecorator {
    private ArrayList<MoveRule> moveRules;
    private int pawnNumber;
    private Map map;

    MoveDecorator(){
        moveRules = new ArrayList<>();
    }

    void addRule(MoveRule moveRule){
        moveRules.add(moveRule);
    }

    public ArrayList<MoveRule> getMoveRules() {
        return moveRules;
    }

    public int getPawnNumber(){
        return pawnNumber;
    }

    public void setMap(Map map){
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    void removeRule(MoveRule moveRuleToRemove){
        for(MoveRule moveRule : moveRules){
            if (moveRule.getClass().equals(moveRuleToRemove.getClass())){
                moveRules.remove(moveRule);
                return;
            }
        }
    }

    public boolean checkMove(ArrayList<MapPoint> mapPoints, AbstractPlayer abstractPlayer){
        sortRules();
        resetRules();
        boolean moveApplied = false;

        if (mapPoints.size() == 0 || mapPoints.size() == 1){
            return false;
        }

        while (true){
            boolean changed = false;
            for(MoveRule moveRule : moveRules){
                int result = moveRule.checkMove(map, mapPoints, abstractPlayer, moveApplied, true);

                System.out.println("RESULT INT: " + result);

                //TODO: TEST THIS
                if( result + 1 == mapPoints.size()){
                    return true;
                }
                if( result != -1){
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



    private void resetRules(){
        for(MoveRule moveRule : moveRules){
            moveRule.reset();
        }
    }

    void setPawnNumber(int pawnNumber) {
        this.pawnNumber = pawnNumber;
    }

    private void sortRules() {
        moveRules.sort(Comparator.comparing(MoveRule::getPriority));
    }




}
