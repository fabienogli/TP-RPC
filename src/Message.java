public class Message {

    public static String sendFile() {
        return "can I send a file ?";
    }

    public static String ack() {
        return "ok";
    }

    public static String choices() {
        StringBuilder str = new StringBuilder();
        str.append("What do you want to do ?)\n[")
                .append(ClientApp.getSourceColl())
                .append("]Code source\n[")
                .append(ClientApp.getByteColl())
                .append("]Code compilé\n[")
                .append(ClientApp.getObjectColl())
                .append("]Objet sérialisé\n\n[")
                .append(ClientApp.getQuit())
                .append("]Quit\"");
        return str.toString();
    }

    public static String connect() {
        return "connexion";
    }

    public static String getNameOfFile() {
        return "Quel est le nom de votre fichier ?";
    }

}
