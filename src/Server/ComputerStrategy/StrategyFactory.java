package Server.ComputerStrategy;

import java.util.ArrayList;

/**@author Damian Nowak
 * Returns the strategy based on avaiable and requested options
 * Strategy factor uses singleton design pattern becasuse
 * there is no need for different strategies instances in different games
 * (strategies are deterministic)
 */
public class StrategyFactory {
    private ArrayList<Strategy> strategies;

    //returns factory
    public synchronized static StrategyFactory getStrategyFactory(){
        if (strategyFactory == null){
            strategyFactory = new StrategyFactory();
        }
        return strategyFactory;
    }

    //returns strategy based on the request
    public Strategy getStrategy(String request){
        //different strategies can be requested by the same AI
        int result = 0;
        //currently used: convert to int and get modulo
        for(char c : request.toCharArray()){
            result+= c;
        }
        result = result % strategies.size();
        return strategies.get(result);
    }

    //instance of the strategy
    private static StrategyFactory strategyFactory;

    //crates and fills the strategy lists
    private StrategyFactory(){
        strategies = new ArrayList<>();
        strategies.add(new AggressiveStrategy());
        //yadi yadi yada
    }



}
