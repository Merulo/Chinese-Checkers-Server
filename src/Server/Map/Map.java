package Server.Map;

//TODO: COPY MY CODE FROM CLIENT - MAP

public class Map {
    //pawns count
    private int pawnNumber;
    //array with map
    private Field map[][];
    //set the pawn number
    public Map(int pawnNumber){
        this.pawnNumber = pawnNumber;
    }

    //create map if null
    public void setUpMap(){
        if (map == null) {
            int size = calculateRowsNumber();
            map = new Field[size][];
            for (int i = 0; i < map.length; i++) {
                map[i] = new Field[size];
            }
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

}
