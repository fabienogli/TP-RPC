import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;
    private static String _IP = "127.0.0.1";
    private static int _PORT = 2025;

    public Client() throws IOException{
        this(_IP, _PORT);
        _PORT++;
    }

    public Client(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
    }

    public String getIp() {
        return this.socket.getInetAddress().getHostAddress();
    }

    public int getPort() {
        return this.socket.getPort();
    }

    public Socket getSocket() {
        return this.socket;
    }

    public static void main(String[] args) {
        Client client;
        try {
            client = new Client();
        } catch (IOException e) {
            System.out.println("Problème dans l'instanciation du client");
            e.printStackTrace();
            return;
        }
        try {
            System.out.println("Le client se connecte");
            Communication.write(client.socket, "connexion");
        } catch (IOException e) {
            System.out.println("Problème pour écriture côté client");
            e.printStackTrace();
        }
    }
}
