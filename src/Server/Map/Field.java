package Server.Map;

import Server.Player.AbstractPlayer;

public class Field {

    //value determining whether this field is part of the game map
    private boolean partOfMap;
    //player on the field
    private AbstractPlayer playerOnField;
    //the player of which the field is home
    private AbstractPlayer homePlayer;

    //default creation
    public Field(){
        partOfMap = false;
        playerOnField = null;
        homePlayer = null;
    }
    public Field copy(){
        Field field = new Field();
        field.setPartOfMap(partOfMap);
        field.setPlayerOnField(playerOnField);
        field.setHomePlayer(homePlayer);
        return field;
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
    //returns home player
    public AbstractPlayer getHomePlayer() {
        return homePlayer;
    }
    //sets home player
    public void setHomePlayer(AbstractPlayer homePlayer) {
        this.homePlayer = homePlayer;
    }
}
