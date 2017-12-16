package Server.Map;

public class Map {
    private int pawnNumber;
    private Field map[][];

    public Map(int pawnNumber){
        this.pawnNumber = pawnNumber;
    }

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

    public Field getField(MapPoint mapPoint){
        if (mapPoint != null){
            return map[mapPoint.getX()][mapPoint.getY()];
        }
        return null;
    }

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
