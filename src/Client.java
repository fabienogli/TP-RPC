//import clientFiles.Test;

import clientFiles.Test;

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
            communication.write(file.getName());
            communication.write(String.valueOf(file.length()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void sourceColl(String _class, String method) {
        String extension = ".java";
        _class += extension;
        File file = FileService.getFile(this, _class);
        sendFileInfo(file);
        try {
            communication.sendFile(file);
            communication.write(method);
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



    public String testObjectColl() {
        String _class ="Test";
        String method = "add";
        String answer = "";
        try {
            communication.write(method);
            answer = communication.read();
            objectColl(FileService.getObject(_class));
            answer = communication.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }
//
    public String testByteColl() {
        String _class = "Test";
        String method = "add";
        String answer = "";
        byteColl(_class, method);
        try {
            answer = communication.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;

    }
//
    public String testSourceColl() {
        String _class = "Test";
        String method = "add";
        String answer = "";
        sourceColl(_class, method);
        try {
            answer = communication.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }

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
//            String answer = client.testObjectColl();
//            String answer = client.testSourceColl();
            String answer = client.testByteColl();
            System.out.println(answer);
//
//
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Client client = null;
//        FileService.compile(client, FileService.getFile(client, "Test.java"));
    }

}
