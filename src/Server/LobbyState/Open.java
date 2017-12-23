package Server.LobbyState;

/**CONDITION: (ONE MUST BE TRUE)
 * Lobby is not full
 * BEHAVIOUR:
 * Allow changing settings
 * Allow new players
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
