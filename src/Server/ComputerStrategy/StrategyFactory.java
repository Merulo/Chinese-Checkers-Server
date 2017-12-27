package Server.ComputerStrategy;

import java.util.ArrayList;

public class StrategyFactory {
    private ArrayList<Strategy> strategies;

    //returns factory
    public synchronized static StrategyFactory getStrategyFactor(){
        if (strategyFactory == null){
            strategyFactory = new StrategyFactory();
        }
        return strategyFactory;
    }

    //returns strategy based on the request
    public Strategy getStrategy(String request){
        int result = 0;
        for(char c : request.toCharArray()){
            result+= c;
        }
        result = result % strategies.size();
        return strategies.get(result);
    }

    private static StrategyFactory strategyFactory;

    private StrategyFactory(){
        strategies = new ArrayList<>();
        strategies.add(new AggressiveStrategy());
        //yadi yadi yada
    }



}
