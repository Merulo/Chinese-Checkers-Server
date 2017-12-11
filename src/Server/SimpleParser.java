package Server;

//TODO: THINK ABOUT MAKING THIS CLASS A SINGLETON?
public class SimpleParser {
    public static String parse(String message, String delimiter){
        if (message.indexOf(delimiter) != -1)
            return message.substring(0, message.indexOf(delimiter));
        return message;
    }
    public static String parse(String message){
        return parse(message, ";");
    }

    public static String cut(String message, String delimiter){
        if (message.indexOf(delimiter) != -1)
                if(message.indexOf(delimiter) + 1 < message.length())
            return message.substring(message.indexOf(delimiter) + 1);
        return message;
    }
    public static String cut(String message){
        return cut(message, ";");
    }
}
