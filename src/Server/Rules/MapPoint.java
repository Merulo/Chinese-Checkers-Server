package Server.Rules;

import java.util.Map;

public class MapPoint {
    private int x;
    private int y;

    MapPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    MapPoint(){
        x = 0;
        y = 0;
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int metric(MapPoint target){
        return Math.abs(x - target.getX()) + Math.abs(y - target.getY()) - (Math.abs(target.getX() - x - (target.getY() - y) ))/2;
    }
}
