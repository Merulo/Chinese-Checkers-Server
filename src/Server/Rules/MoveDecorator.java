package Server.Rules;

import Server.Map.Map;
import Server.Map.MapPoint;

import java.util.*;

public class MoveDecorator {
    private List<MoveRule> moveRules;
    private int pawnNumber;
    private Map map;

    public MoveDecorator(){
        moveRules = new ArrayList<>();
    }

    public void addRule(MoveRule moveRule){
        moveRules.add(moveRule);
    }

    public void removeRule(MoveRule moveRule){
        //TODO: ADD REMOVING RULES
        //THIS DOESN'T WORK
        //moveRules.remove(moveRule);
    }

    private void resetRules(){
        for(MoveRule moveRule : moveRules){
            moveRule.reset();
        }
    }

    public void setPawnNumber(int pawnNumber) {
        this.pawnNumber = pawnNumber;
    }

    private void sortRules() {
        moveRules.sort(Comparator.comparing(MoveRule::getPriority));
    }

    private void checkMap(){
        if (map == null){
            map = new Map(pawnNumber);
            map.setUpMap();
        }
    }

    public boolean checkMove(ArrayList<MapPoint> mapPoints, int playerID){
        sortRules();
        checkMap();
        resetRules();

        while (true){
            boolean changed = false;
            for(MoveRule moveRule : moveRules){
                int result = moveRule.checkMove(map, mapPoints, playerID);

                //TODO: TEST THIS
                if( result == mapPoints.size()){
                    return true;
                }
                //TODO: TEST THIS
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
