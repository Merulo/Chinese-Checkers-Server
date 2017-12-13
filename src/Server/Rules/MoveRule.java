package Server.Rules;

public interface MoveRule {
    boolean checkMove(int map[][], MapPoint oldPoint, MapPoint newPoint, int playerID);

}
