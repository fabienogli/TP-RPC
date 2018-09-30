//import upload.Test;

import upload.Test;

import java.io.*;
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
        String result = sendFile(file);
    }

    private String sendFile(File file) {
        try {
            Communication.sendFile(file, socket);
            return Message.ack();
        } catch (IOException e) {
            e.printStackTrace();
            return Message.getErrorSendFile();
        }
    }

    public void byteColl(File file) {
        File compiled = FileService.compile(file);
        String result = sendFile(compiled);
    }


    public void objectColl(File file) {
        Test test = new Test();
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
            client.objectColl(FileService.getFile(client, "Test.java"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void sendFile(String _file) throws IOException {
        File file = FileService.getFile(_file);
        Communication.write(socket, String.valueOf(file.length()));
        Communication.sendFile(file, socket);
    }
}
