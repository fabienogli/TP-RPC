package util;

public class Message {

    static String sourceColl = "1";
    static String byteColl = "2";
    static String objectColl = "3";
    static String quit = "0";
    static String emptyResult = "O_O";


    public static String ack() {
        return "ok";
    }

    public static String goodChoice() {
        return "bon choix";
    }

    public static String choices() {
        StringBuilder str = new StringBuilder();
        str.append("Que voulez vous faire ?\n[")
                .append(sourceColl)
                .append("]Code source\n[")
                .append(byteColl)
                .append("]Code compilé\n[")
                .append(objectColl)
                .append("]Objet sérialisé\n\n[")
                .append(quit)
                .append("]Quit");
        return str.toString();
    }

    public static String getSourceColl() {
        return sourceColl;
    }

    public static String getByteColl() {
        return byteColl;
    }

    public static String getObjectColl() {
        return objectColl;
    }

    public static String getEmptyResult() {
        return emptyResult;
    }

    public static String getAnswer(String s) {
        return "Answer = " + s + "\n\n";
    }

    public static String getWrongChoice() {
        return "Cette option n'existe pas";
    }

    public static String getQuit() {
        return quit;
    }
}
