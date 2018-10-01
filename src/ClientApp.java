import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ClientApp {

    static String sourceColl = "1";
    static String byteColl = "2";
    static String objectColl = "3";
    static String quit = "q";

    public static String readConsole() {
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        return str;
    }

    public static void main(String[] args) {
        Client client;
        try {
            client = new Client();
            System.out.println("Le client se connecte");
            if (!client.connect()) {
                return;
            }
            System.out.println(Message.choices());
            String chosen = readConsole();
            System.out.println(Message.getNameOfFile());
//            File file = null;
//            while (file == null) {
//                String s_file = readConsole();
//                if (s_file.equals(getQuit())) {
//                    client.quit();
//                    return;
//                }
//                file = FileService.getFile(s_file);
//            }
//            if (chosen.equals(sourceColl)) {
//                client.sourceColl(file);
//            } else if (chosen.equals(byteColl)) {
//                client.byteColl(file);
//            } else if (chosen.equals(objectColl)) {
//                client.objectColl(file);
//            } else {
//                client.quit();
//            }
        } catch (IOException e) {
            System.out.println("Problème dans l'instanciation du client");
            e.printStackTrace();
            return;
        }
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
}
