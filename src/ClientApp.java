import java.io.IOException;
import java.util.Scanner;

public class ClientApp {

    static Scanner scanner = new Scanner(System.in);

    public static String readConsole() {
        String str = scanner.next();
        return str;
    }

    public static void main(String[] args) {
        Client client;
        try {
            client = new Client();

            System.out.println(Message.choices());
            String chosen = readConsole();
            client.getCommunication().write(chosen);
            if (!client.getAnswer().contains(Message.ack())) {
                System.out.println("La réponse est inattendue");
            }
            System.out.println("Choisissez la classe");
            String _class = readConsole();
            System.out.println("Choisissez la méthode");
            String method = readConsole();
            System.out.println(chosen);
            System.out.println(Message.getObjectColl());
            if (Message.getObjectColl().contains(chosen)) {
                client.objectColl(_class, method);
            } else if (Message.getByteColl().contains(chosen)) {
                client.byteColl(_class, method);
            } else if (Message.getSourceColl().contains(chosen)) {
                client.sourceColl(_class, method);
            } else {
                System.out.println("rien");
            }
            String answer = client.getAnswer();
            System.out.println(answer);
//            System.out.println(Message.getNameOfFile());
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
//            } else if (chosen.equals(sendObject)) {
//                client.sendObject(file);
//            } else {
//                client.quit();
//            }
        } catch (IOException e) {
            System.out.println("Problème dans l'instanciation du client");
            e.printStackTrace();
            return;
        }
    }

    public void sourceColl(String _class, String method) {
        try {
            Client client = new Client();
            client.sourceColl(_class, method);
            String answer = client.getAnswer();
            System.out.println(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void objectColl(String _class, String method) {
        try {
            Client client = new Client();
            client.objectColl(_class, method);
            String answer = client.getAnswer();
            System.out.println(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void byteColl(String _class, String method) {
        try {
            Client client = new Client();
            client.byteColl(_class, method);
            String answer = client.getAnswer();
            System.out.println(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
