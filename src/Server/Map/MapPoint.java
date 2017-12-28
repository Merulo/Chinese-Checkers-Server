package Server.Map;

public class MapPoint {
    //coordinates of the point
    private int x;
    private int y;

    //sets the x and y
    public MapPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void print(){
        System.out.println("(" + x + "," + y + ")");
    }

    //default constructor
    public MapPoint(){
        x = 0;
        y = 0;
    }

    //gets x
    public int getX() {
        return x;
    }

    public MapPoint copy(){
        return new MapPoint(x, y);
    }

    //gets y
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    //calculates distance according to our metric
    public int getDistance(MapPoint target){

        if(x == target.getX()){
            return Math.abs(x - target.getX()) + Math.abs(y - target.getY());
        }

        if(y == target.getY()) {
            return Math.abs(x - target.getX()) + Math.abs(y - target.getY());
        }

        int counter = 0;
        int tx1 = x;
        int ty1 = y;

        if(x > target.getX() && y > target.getY()){
            return Math.abs(x - target.getX()) + Math.abs(y - target.getY());
        }
        if(x < target.getX() && y < target.getY()){
            return Math.abs(x - target.getX()) + Math.abs(y - target.getY());
        }

        while(tx1 != target.getX() && ty1 != target.getY()){
            if(x < target.getX()){
                tx1++;
                ty1--;
            }
            else{
                tx1--;
                ty1++;
            }
            counter++;
        }

        return Math.abs(x - target.getX()) + Math.abs(y - target.getY()) - counter;
    }

    public boolean areAligned(MapPoint target){
        int dx = target.getX() - x;
        int dy = target.getY() - y;

        if (dx == 0 || dy == 0) {
            return true;
        }
        //third axis
        return (dx == -dy);
    }
}
