package Server.Map;

import Server.Player.AbstractPlayer;

public class Field {
    boolean partOfMap;
    AbstractPlayer playerOnField;

    public Field(){
        partOfMap = false;
        playerOnField = null;
    }

    public AbstractPlayer getPlayerOnField(){
        return playerOnField;
    }

    public Boolean getPartOfMap(){
        return partOfMap;
    }

    public void setPartOfMap(boolean partOfMap) {
        this.partOfMap = partOfMap;
    }

    public void setPlayerOnField(AbstractPlayer playerOnField) {
        this.playerOnField = playerOnField;
    }
}
