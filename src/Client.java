//import clientFiles.Test;

import java.io.*;
import java.net.Socket;
import java.util.Optional;

public class Client {

    private Socket socket;
    private static String _IP = "127.0.0.1";
    private static int _PORT = 2025;
    Communication communication;

    public Client() throws IOException{
        this(_IP, _PORT);
        _PORT++;
        communication = new Communication(socket);
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
        communication.write(Message.connect());
        String answer = communication.read();
        if (!answer.equals(Message.ack())) {
           quit();
           return false;
        }
        return true;
    }

    public void quit() throws IOException {
        socket.close();
    }

    private void sendFileInfo(File file) {
        try {
//            System.out.println(file.getName());
//            System.out.println(file.length());
//            System.out.println(String.valueOf(file.length()));
            communication.write(file.getName());
            communication.write(String.valueOf(file.length()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void sourceColl(String _class) {
        String extension = ".java";
        _class += extension;
        File file = FileService.getFile(this, _class);
        sendFileInfo(file);
        try {
            communication.sendFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void byteColl(String _class, String method) {
        String extension = ".class";
        _class += extension;
        File file = FileService.getFile(this, _class);
        sendFileInfo(file);
        try {
            communication.sendFile(file);
            communication.write(method);
            communication.write(method);
//            String answer = communication.read(socket);
//            System.out.println(answer);
//            System.out.println("réponse après envoie fichier et info fichier: " + answer);
//            System.out.println("Le client a envoyé la méthode:" + method);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void objectColl(Object object) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(object);
            System.out.println("objet envoyé");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("<BPPB");
        }
    }

    /**
     * TESTS
     *
     *
     *
     */



//    public void testObjectColl(Client client) {
//        String _class ="Test";
//        String method = "add";
//        try {
//            communication.write(client.socket, method);
//            String answer = communication.read(client.socket);
//            System.out.println(answer);
//            client.objectColl(FileService.getObject(_class));
//            answer = communication.read(client.socket);
//            System.out.println(answer);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void testByteColl(Client client) {
//        String _class = "Test";
//        String method = "add";
//        client.byteColl(_class, method);
//
//    }
//
//    public static void testSourceColl(Client client) {
//        String _class = "Test";
//        String method = "add";
//        client.sourceColl(_class);
//        try {
//            System.out.println(communication.read(client.socket));
//            communication.write(client.socket,method);
//            String result = communication.read(client.socket);
//            System.out.println(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public void testSendFile() {
        File file = FileService.getFile(this, "Test.class");
        sendFileInfo(file);
        try {
            communication.sendFile(file);
            communication.write("fini");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        try {
            Client client = new Client();
//            client.testSendFile(client);
//            String result = client.communication.read();
//            client.communication.write("add");
//            client.communication.write("add");
            client.byteColl("Test", "add");
            String s = client.communication.read();
            System.out.println(s);
//
//
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Client client = null;
//        FileService.compile(client, FileService.getFile(client, "Test.java"));
    }

}
