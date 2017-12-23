package Server.Map;

import Server.Player.AbstractPlayer;

public class Field {

    //value determining whether this field is part of the game map
    private boolean partOfMap;
    //play on the field
    private AbstractPlayer playerOnField;

    //default creation
    public Field(){
        partOfMap = false;
        playerOnField = null;
    }
    //returns the player
    public AbstractPlayer getPlayerOnField(){
        return playerOnField;
    }
    //returns true if field is part of map
    public Boolean getPartOfMap(){
        return partOfMap;
    }
    //sets part of the map
    public void setPartOfMap(boolean partOfMap) {
        this.partOfMap = partOfMap;
    }
    //sets player
    public void setPlayerOnField(AbstractPlayer playerOnField) {
        this.playerOnField = playerOnField;
    }
}
