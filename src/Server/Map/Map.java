package Server.Map;

import Server.Player.AbstractPlayer;

import java.util.ArrayList;

public class Map {
    //pawns count
    private int pawnNumber;
    //array with map
    private Field map[][];
    //list with players
    private ArrayList<AbstractPlayer> players;

    //set the pawn number
    public Map(int pawnNumber){
        this.pawnNumber = pawnNumber;
    }

    public Map copy(){
        Map map = new Map(pawnNumber);
        map.setPlayers(players);
        map.setUpMap();

        for (int i = 0; i < this.map.length; i++) {
            for(int j = 0; j < this.map[0].length; j++){
                map.setField(i, j, this.map[i][j].copy());
            }
        }


        return map;
    }

    public void setPlayers(ArrayList<AbstractPlayer> players) {
        this.players = players;
    }

    //create map if null
    public void setUpMap(){
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
            setUpStartingPosition();
        }
        //printMap();
    }

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

    public void replacePlayer(AbstractPlayer oldplayer, AbstractPlayer newplayer){
        for(Field fieldArray[] : map) {
            for (Field field : fieldArray) {
                if(field.getPlayerOnField() == oldplayer){
                    field.setPlayerOnField(newplayer);
                }
                if(field.getHomePlayer() == oldplayer){
                    field.setHomePlayer(newplayer);
                }
            }
        }
    }

    public void printMap(){
        for(Field fieldArray[] : map){
            for(Field field : fieldArray){
                if(field.getPartOfMap()){
                    if(field.getPlayerOnField()!=null) {
                        System.out.print("P");
                    }
                    else{
                        System.out.print("N");
                    }
                }
                else{
                    System.out.print("X");
                }
            }
            System.out.println("");
        }
        /*
        for(Field fieldArray[] : map){
            for(Field field : fieldArray){
                if(field.getPartOfMap()){
                    if(field.getHomePlayer()!=null) {
                        System.out.print("H");
                    }
                    else{
                        System.out.print("N");
                    }
                }
                else{
                    System.out.print("X");
                }
            }
            System.out.println("");
        }*/
    }

    public void printMap(MapPoint mapPoint){
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(i == mapPoint.getX() && j == mapPoint.getY()){
                    System.out.print("C");
                }
                else if(map[i][j].getPartOfMap()){
                    if(map[i][j].getPlayerOnField()!=null) {
                        System.out.print("P");
                    }
                    else{
                        System.out.print("N");
                    }
                }
                else{
                    System.out.print("X");
                }
            }
            System.out.println("");
        }
    }

    private void setUpStartingPosition(){
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

    private void setField(int i, int j, Field f){
        map[i][j] = f;
    }
}
