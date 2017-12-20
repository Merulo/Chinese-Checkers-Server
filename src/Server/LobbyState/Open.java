package Server.LobbyState;

//TODO: UPDATE CONDITIONS
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
        lobbyState.setState(new Full());
    }
    @Override
    public void changeStatePrev(LobbyState lobbyState){

    }
    @Override
    public void handleLobby(LobbyState lobbyState){
        if (lobbyState.getLobby().isFull()){
            lobbyState.nextPhase();
        }
    }
    @Override
    public String getName(){
        return "Open";
    }
}
