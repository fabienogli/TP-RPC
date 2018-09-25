import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connexion implements Runnable {
    private Socket client;
    private boolean active;

    public Connexion(Socket socket) {
        client = socket;
        active = true;
    }

    @Override
    public void run() {
        String recu = "";
        try {
            int filesize = Integer.parseInt(Communication.read(client));
            receivedFile("received", filesize);
//            recu = Communication.read(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(recu);
    }

    public void receivedFile(String string, int filesize) {
        String file = string;
        try {
            Communication.saveFile(string, client, filesize);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
