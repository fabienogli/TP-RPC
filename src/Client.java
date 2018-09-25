import java.io.IOException;
import java.net.Socket;

public class Client {

    private Socket socket;
    private static String _IP = "127.0.0.1";
    private static int _PORT = 2025;

    public Client() throws IOException{
        this(_IP, _PORT);
    }

    public Client(String ip, int port) throws IOException {
        this.socket = new Socket(ip, port);
    }

}
