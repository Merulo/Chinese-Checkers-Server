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
        return Math.abs(x - target.getX()) + Math.abs(y - target.getY()) - (Math.abs(target.getX() - x - (target.getY() - y) ))/2;
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
