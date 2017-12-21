package Server.Network;

import Server.Map.Map;
import Server.Player.AbstractPlayer;
import Server.Rules.MoveDecorator;
import Server.SimpleParser;

import java.util.List;

public class Game implements NetworkManager {
    //list of players
    volatile private List<AbstractPlayer> players;
    //the main hub
    private Hub hub;
    //the main lobby
    private Lobby lobby;
    //map
    private Map map;
    //MoveDecorator
    private MoveDecorator moveDecorator;
    //strategy factory
    //etc

    Game(List<AbstractPlayer> players, Hub hub, Lobby lobby){
        this.hub = hub;
        this.lobby = lobby;
        this.players = players;
    }


    @Override
    public synchronized void addPlayer(AbstractPlayer player) {
        //no more adding players : )
    }

    @Override
    public synchronized void removePlayers(){
        try {
            //TODO: HOW TO DEAL WITH LEAVING PLAYER?
            //TODO: PROBABLY CREATE BOT IN HIS PLACE

            for(int i = 0; i < players.size(); i++){
                if(!players.get(i).isPlaying()){
                    for(AbstractPlayer abstractPlayer : players){
                        if(abstractPlayer != players.get(i))
                            abstractPlayer.sendMessage( "Remove:" +  players.get(i).getNick() + ";");
                    }
                    players.remove(i);
                    i = i - 1;
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        hub.sendGame(lobby);
    }

    //moves the player from game to hub
    @Override
    public synchronized void enter(AbstractPlayer client, int number){
        //TODO: HOW TO DEAL WITH LEAVING PLAYER?
        //TODO: PROBABLY CREATE BOT IN HIS PLACE

        System.out.println("MOVING FROM GAME TO HUB");
        players.remove(client);
        for(AbstractPlayer abstractPlayer : players){
            abstractPlayer.sendMessage("Remove;" + client.getNick() + ";");
        }

        hub.sendGame(lobby);
        hub.addPlayer(client);
    }

    @Override
    public synchronized void parse(AbstractPlayer abstractPlayer, String message){
        String type = SimpleParser.parse(message);
        switch (type) {
            case "Move": {
                break;
            }
            case "Msg":{
                break;
            }
        }
    }



}
