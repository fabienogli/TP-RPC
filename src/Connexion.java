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
            recu = Communication.read(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(recu);
    }
}
