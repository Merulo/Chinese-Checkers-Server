package Server.Rules;

import java.util.ArrayList;
import java.util.List;

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

    public boolean checkMove(MapPoint oldPoint, MapPoint newPoint, int playerID){
        //TODO: REWORK THIS CODE!

        for(MoveRule moveRule : moveRules){
            if (!moveRule.checkMove(map, oldPoint, newPoint, playerID)){
                return false;
            }
        }
        map[newPoint.getX()][newPoint.getY()] = map[oldPoint.getX()][oldPoint.getY()];
        map[oldPoint.getX()][oldPoint.getY()] = -1;


        return true;
    }





}
