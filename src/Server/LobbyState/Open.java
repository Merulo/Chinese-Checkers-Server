package Server.LobbyState;

/**CONDITION: (ONE MUST BE TRUE)
 * Not all players are ready
 * Incorrect number of players
 * BEHAVIOUR:
 * Allow changing settings
 * Allow adding bots
 * Allow new players if there is space
*/
public class Open implements State {
    @Override
    public void changeStateNext(LobbyState lobbyState){
        lobbyState.setState(new ReadyToStart());
    }
    @Override
    public void changeStatePrev(LobbyState lobbyState){
        //no previous state
        return;
    }
    @Override
    public void handleLobby(LobbyState lobbyState){
        //logic here!
    }
    @Override
    public String getName(){
        return "Open";
    }
}
