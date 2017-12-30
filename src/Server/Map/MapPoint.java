package Server.Map;

/**@author Damian Nowak
 * 2D point with metric and alligment methods
 */
public class MapPoint {
    //coordinates of the point
    private int x;
    private int y;

    //sets the x and y
    public MapPoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    //prints the point coordinates
    public void print(){
        System.out.println("(" + x + "," + y + ")");
    }

    //default constructor
    public MapPoint(){
        x = 0;
        y = 0;
    }

    //returns
    public MapPoint copy(){
        return new MapPoint(x, y);
    }

    //gets x
    public int getX() {
        return x;
    }

    //gets y
    public int getY() {
        return y;
    }

    //calculates distance according to our metric
    public int getDistance(MapPoint target){
        if(x == target.getX() && y == target.getY()){
            return 0;
        }

        if(x == target.getX() || y == target.getY()){
            return Math.abs(x - target.getX()) + Math.abs(y - target.getY());
        }

        int counter = 0;
        int tx1 = x;
        int ty1 = y;

        if((x > target.getX() && y > target.getY()) || (x < target.getX() && y < target.getY())){
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

    //returns true if points are Aligned
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
