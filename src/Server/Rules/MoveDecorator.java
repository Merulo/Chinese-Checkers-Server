package Server.Rules;

import java.util.*;

public class MoveDecorator {
    private int map[][];
    private List<MoveRule> moveRules;

    public MoveDecorator(int pawnNumber){
        //TODO: REPLACE WITH CODE CALCULATED FROM pawnNumber
        int size = 10;
        map = new int[size][];
        for(int i =0; i < map.length; i++){
            map[i] = new int [size];
            for(int j =0; j < map[i].length; j++){
                map[i][j] = -1;
            }
        }
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

    private void sortRules() {
        moveRules.sort(Comparator.comparing(MoveRule::getPriority));
    }

    public boolean checkMove(ArrayList<MapPoint> mapPoints, int playerID){

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
