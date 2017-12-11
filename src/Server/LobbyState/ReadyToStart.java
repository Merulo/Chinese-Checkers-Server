package Server.LobbyState;

/**CONDITION: (ALL MUST BE TRUE)
 * All players are ready
 * Correct number of players
 * BEHAVIOUR:
 * No changes allowed
 * No joining allowed
 * Counting down from 10 to 0 to begin game
 */
public class ReadyToStart implements State {
    @Override
    public void changeStateNext(LobbyState lobbyState){
        lobbyState.setState(new Playing());
    }
    @Override
    public void changeStatePrev(LobbyState lobbyState){
        lobbyState.setState(new Open());
    }
    @Override
    public void handleLobby(LobbyState lobbyState){

    }
    @Override
    public String getName(){
        return "Ready to start";
    }
}
