package Server.LobbyState;

/**@author Damian Nowak
 * CONDITION: (ONE MUST BE TRUE)
 * At least one human player still playing the game
 * BEHAVIOUR:
 * Handle player messages
 * Handle game logic
 */
public class Playing implements State {
    @Override
    public void changeStateNext(LobbyState lobbyState){
        lobbyState.setState(new Resetting());
    }
    @Override
    public void changeStatePrev(LobbyState lobbyState){
        //lobby can't go back to readyToStart state
        lobbyState.setState(new Resetting());
    }
    @Override
    public void handleLobby(LobbyState lobbyState){
        if(lobbyState.getGame().areThereOnlyBotsInGame()){
            lobbyState.nextPhase();
        }
    }
    @Override
    public String getName(){
        return "Playing";
    }
}
