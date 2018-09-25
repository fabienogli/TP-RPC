import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private ServerSocket socket;
    private static int _PORT = 2025;

    public Server() throws IOException {
        this(_PORT);
    }

    public Server(int port) throws IOException {
        this.socket = new ServerSocket(port);
    }

    public static int getPort() {
        return _PORT;
    }

    public Socket getClient() throws IOException {
        Socket socket = this.socket.accept();
        return socket;
    }

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();
        } catch (IOException e) {
            System.out.println("Problème dans instanciation de server");
            e.printStackTrace();
            return;
        }
        try {
            while (true){
                Socket socket = server.getClient();
                Thread thread = new Thread(new Connexion(socket));
                thread.run();
            }
        } catch (IOException e) {
            System.out.println("Problème pour récuperer le client");
            e.printStackTrace();
        }
    }
}
