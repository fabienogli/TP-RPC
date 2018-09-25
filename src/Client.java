import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.net.URLClassLoader;

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

    /**
     * The customer will send his file to the server
     * @param file
     * @param socket
     * @throws IOException
     */
    public void sendFile(File file, Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        while (fis.read(buffer) > 0) {
            dos.write(buffer);
        }
        fis.close();
    }

    public boolean connect() throws IOException {
        Communication.write(socket, Message.connect());
        String answer = Communication.read(socket);
        if (!answer.equals(Message.ack())) {
           quit();
           return false;
        }
        return true;
    }

    public void quit() throws IOException {
        socket.close();
    }


    /**
     * Le client peut envoyer les requêtes au serveur en trois façons selon le protocole collaboratif suivant :
     * 1) SOURCEColl : Code source de(s) la classe(s) demandé(es) ou
     * 2) BYTEColl : Bytecode Code compilé de ces classes ou
     * 3) OBJECTColl : Objets de ces classes
     */

    public void sourceColl(File file) {
        try {
            sendFile(file, socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void byteColl(File file) {
        FileService.compile(file);
    }


    public void objectColl(File file) {

    }
}
