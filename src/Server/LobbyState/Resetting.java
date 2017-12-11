package Server.LobbyState;

/**CONDITION: (ONE MUST BE TRUE)
 * No human player in game
 * BEHAVIOUR:
 * Reset variables
 * Set state to open
 */
public class Resetting implements State {
    @Override
    public void changeStateNext(LobbyState lobbyState){
        lobbyState.setState(new Open());
    }
    @Override
    public void changeStatePrev(LobbyState lobbyState){
        //lobby can't go back to Playing state
        lobbyState.setState(new Open());
    }
    @Override
    public void handleLobby(LobbyState lobbyState){

    }
    @Override
    public String getName(){
        return "Resetting";
    }
}
