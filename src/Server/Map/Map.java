package Server.Map;

import Server.Player.AbstractPlayer;
import java.util.ArrayList;

/**@author Damian Nowak
 * Contains 2D array of fields and assigns players to each field
 */
public class Map {
    //pawns count
    private int pawnNumber;
    //array with map
    private Field map[][];

    //set the pawn number
    public Map(int pawnNumber){
        this.pawnNumber = pawnNumber;
    }

    //returns the new instance of map identical with the one this method is called on
    public Map copy(){
        Map map = new Map(pawnNumber);
        map.setUpMap(null);

        for (int i = 0; i < this.map.length; i++) {
            for(int j = 0; j < this.map[0].length; j++){
                map.setField(i, j, this.map[i][j].copy());
            }
        }
        return map;
    }

    //create map if map is empty
    public void setUpMap(ArrayList<AbstractPlayer> players){
        if (map == null) {
            int rows = calculateRowsNumber();
            int size = rows*4 + 1;
            map = new Field[size][];
            for (int i = 0; i < map.length; i++) {
                map[i] = new Field[size];
                for(int j = 0; j < size; j++){
                    map[i][j] = new Field();
                }
            }

            for(int i = rows; i < size - rows; i++){
                for(int j = rows; j < size - rows; j++){
                    map[i][j].setPartOfMap(true);
                    map[i][j].setPlayerOnField(null );
                }
            }
            for(int i = 0; i < rows; i++) {
                for (int j = 0; j < rows - i; j++) {
                    map[rows - i - 1][rows - j + rows * 2].setPartOfMap(true);
                    map[i + rows][j + rows].setPartOfMap(true);
                    map[rows - i + rows * 2][rows - j - 1].setPartOfMap(true);
                    map[i + rows * 3 + 1][j + rows].setPartOfMap(true);
                    map[rows - i + rows * 2][rows - j + rows * 2].setPartOfMap(true);
                    map[i + rows][j + rows *3 + 1].setPartOfMap(true);
                }
            }
            if(players != null) {
                setUpStartingPosition(players);
            }
        }
    }

    //returns true if the given player has won the game
    public boolean checkWin(AbstractPlayer abstractPlayer){
        for(Field fieldArray[] : map) {
            for (Field field : fieldArray) {
                if(field.getHomePlayer() == abstractPlayer){
                    if(field.getPlayerOnField() != abstractPlayer){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    //returns the field under given point
    public Field getField(MapPoint mapPoint){
        if (mapPoint != null){
            if (mapPoint.getX() < 0){
                return null;
            }
            if (mapPoint.getY() < 0){
                return null;
            }
            if (mapPoint.getX() >= map.length){
                return null;
            }
            if (mapPoint.getY() >= map.length){
                return null;
            }
            return map[mapPoint.getX()][mapPoint.getY()];
        }
        return null;
    }

    //returns list of coordinates with given player pawns
    public ArrayList<MapPoint> getMyPawns(AbstractPlayer player){
        ArrayList<MapPoint> mapPoints = new ArrayList<>();
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j].getPlayerOnField() == player){
                    mapPoints.add(new MapPoint(i,j));
                }
            }
        }
        return mapPoints;
    }

    //returns list of coordinates with given player home fields
    public ArrayList<MapPoint> getMyHome(AbstractPlayer player){
        ArrayList<MapPoint> mapPoints = new ArrayList<>();
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j].getHomePlayer() == player){
                    mapPoints.add(new MapPoint(i,j));
                }
            }
        }
        return mapPoints;
    }

    //replaces one player with other
    public void replacePlayer(AbstractPlayer oldPlayer, AbstractPlayer newPlayer){
        for(Field fieldArray[] : map) {
            for (Field field : fieldArray) {
                if(field.getPlayerOnField() == oldPlayer){
                    field.setPlayerOnField(newPlayer);
                }
                if(field.getHomePlayer() == oldPlayer){
                    field.setHomePlayer(newPlayer);
                }
            }
        }
    }

    //sets up starting positions and fills them accordingly
    private void setUpStartingPosition(ArrayList<AbstractPlayer> players){
        int i = 6/players.size();
        if(players.size() == 4){
            fillCorner(1, players.get(0));
            fillCorner(2, players.get(1));
            fillCorner(4, players.get(2));
            fillCorner(5, players.get(3));
        }
        else{
           int counter = 0;
           int x = 0;
           do{
               fillCorner(counter, players.get(x));
               x++;
               counter+= i;
           }while (counter < 6);
        }
    }

    //calculates the necessary number of rows
    private int calculateRowsNumber(){
        int pawns = pawnNumber;
        int row = 0;
        while (pawns > 0){
            row++;
            pawns = pawns - row;
        }
        return row;
    }

    //fills necessary fields for one player
    private void fillCorner(int option, AbstractPlayer player){
        int rows = calculateRowsNumber();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < rows - i; j++) {
                switch (option){
                    case 0:{
                        map[rows - i - 1][rows - j + rows * 2].setPlayerOnField(player);
                        map[i + rows * 3 + 1][j + rows].setHomePlayer(player);
                        break;
                    }
                    case 1:{
                        map[i + rows][j + rows *3 + 1].setPlayerOnField(player);
                        map[rows - i + rows * 2][rows - j - 1].setHomePlayer(player);
                        break;
                    }
                    case 2:{
                        map[rows - i + rows * 2][rows - j + rows * 2].setPlayerOnField(player);
                        map[i + rows][j + rows].setHomePlayer(player);
                        break;
                    }
                    case 3:{
                        map[i + rows * 3 + 1][j + rows].setPlayerOnField(player);
                        map[rows - i - 1][rows - j + rows * 2].setHomePlayer(player);
                        break;
                    }
                    case 4:{
                        map[rows - i + rows * 2][rows - j - 1].setPlayerOnField(player);
                        map[i + rows][j + rows *3 + 1].setHomePlayer(player);
                        break;
                    }
                    case 5: {
                        map[i + rows][j + rows].setPlayerOnField(player);
                        map[rows - i + rows * 2][rows - j + rows * 2].setHomePlayer(player);
                        break;
                    }
                }
            }
        }
    }

    //sets field on given coordinates
    private void setField(int i, int j, Field f){
        map[i][j] = f;
    }
}
