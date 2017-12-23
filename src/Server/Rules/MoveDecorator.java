package Server.Rules;

import Server.Map.Map;
import Server.Map.MapPoint;
import Server.Player.AbstractPlayer;

import java.util.*;

public class MoveDecorator {
    private List<MoveRule> moveRules;
    private int pawnNumber;
    private Map map;

    MoveDecorator(){
        moveRules = new ArrayList<>();
    }

    void addRule(MoveRule moveRule){
        moveRules.add(moveRule);
    }

    public List<MoveRule> getMoveRules() {
        return moveRules;
    }

    void removeRule(MoveRule moveRuleToRemove){
        for(MoveRule moveRule : moveRules){
            if (moveRule.getClass().equals(moveRuleToRemove.getClass())){
                moveRules.remove(moveRule);
                return;
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

    public int getPawnNumber(){
        return pawnNumber;
    }

    private void sortRules() {
        moveRules.sort(Comparator.comparing(MoveRule::getPriority));
    }

    public void setMap(Map map){
        this.map = map;
    }

    public boolean checkMove(ArrayList<MapPoint> mapPoints, AbstractPlayer abstractPlayer){
        sortRules();
        resetRules();

        while (true){
            boolean changed = false;
            for(MoveRule moveRule : moveRules){
                int result = moveRule.checkMove(map, mapPoints, abstractPlayer);

                //TODO: TEST THIS
                if( result == mapPoints.size()){
                    return true;
                }
                if( result != -1){
                    mapPoints.subList(0, result).clear();
                    changed = true;
                    break;
                }
            }
            if (!changed){
                return false;
            }
        }
    }





}
