package Server.Map;

//TODO: COPY MY CODE FROM CLIENT - MAP

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
            setUpStartingPosition();

            //TODO: SETUP MAP IN CORRECT WAY
        }
    }

    //returns the field under given point
    public Field getField(MapPoint mapPoint){
        if (mapPoint != null){
            return map[mapPoint.getX()][mapPoint.getY()];
        }
        return null;
    }

    public void setPlayers(ArrayList<AbstractPlayer> players) {
        this.players = players;
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

    public void printMap(){
        //TODO: REPLACE WITH FOREACH
        for(int i =0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                if(map[i][j].getPartOfMap()){
                    System.out.print("O");
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
    private void fillCorner(int option, AbstractPlayer player){
        int rows = calculateRowsNumber();
        System.out.println("TESTING" + option);
        if(option == 5) {
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < rows - i; j++) {
                    map[i + rows][j + rows].setPartOfMap(true);
                    map[i + rows][j + rows].setPlayerOnField(player);
                }
            }
        }
    }

}
