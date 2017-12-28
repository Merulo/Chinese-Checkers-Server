package Server.LobbyState;

/**@author Damian Nowak
 * CONDITION: (ONE MUST BE TRUE)
 * Lobby is not full
 * BEHAVIOUR:
 * Allow changing settings
 * Allow new players
*/
public class Open implements State {

    //moves to the next step
    @Override
    public void changeStateNext(LobbyState lobbyState){
        lobbyState.setState(new Full());
    }

    //moves to the previous step
    @Override
    public void changeStatePrev(LobbyState lobbyState){

    }

    //handles the lobby in this state
    @Override
    public void handleLobby(LobbyState lobbyState){
        if (lobbyState.getLobby().isFull()){
            lobbyState.nextPhase();
        }
    }

    //returns state name
    @Override
    public String getName(){
        return "Open";
    }
}
