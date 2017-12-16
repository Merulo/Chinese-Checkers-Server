package Server.Map;

public class MapPoint {
    private int x;
    private int y;

    public MapPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    public MapPoint(){
        x = 0;
        y = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDistance(MapPoint target){
        return Math.abs(x - target.getX()) + Math.abs(y - target.getY()) - (Math.abs(target.getX() - x - (target.getY() - y) ))/2;
    }
}
