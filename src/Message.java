public class Message {
    static int wrongConter = 0;

    static String sourceColl = "1";
    static String byteColl = "2";
    static String objectColl = "3";
    static String quit = "0";
    static String emptyResult = "O_O";

    static String message = "message:";

    public static String sendFile() {
        return "can I send a file ?";
    }

    public static String ack() {
        return "ok";
    }

    public static String goodChoice() {
        return "bon choix";
    }

    public static String choices() {
        StringBuilder str = new StringBuilder();
        str.append("What do you want to do ?)\n[")
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

    public static String connect() {
        return "connexion";
    }

    public static String getNameOfFile() {
        return "Quel est le nom de votre fichier ?";
    }

    public static String getMessage() {
        return message;
    }

    public static String getErrorSendFile() {
        return "Il y a eu une erreur dans l'envoi du fichier";
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

    public static String getQuit() {
        return quit;
    }

    public static String getEmptyResult() {
        return emptyResult;
    }

    public static String getWrongChoice() {
        if (wrongConter == 0) {
            wrongConter++;
        }
        return "Vous ne donnez pas le bon choix";
    }
}
