//import clientFiles.Test;

import java.io.*;
import java.net.Socket;

public class Client {

    private Socket socket;
    private static String _IP = "127.0.0.1";
    private static int _PORT = 2025;
    private Communication communication;

    public Client() throws IOException{
        this(_IP, _PORT);
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



    public void sourceColl(String _class, String method, int a, int b) {
        String extension = ".java";
        _class += extension;
        File file = FileService.getFile(this, _class);
        sendFileInfo(file);
        try {
            communication.sendFile(file);
            communication.write(method);
            communication.write(String.valueOf(a));
            communication.write(String.valueOf(b));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void byteColl(String _class, String method, int a, int b) {
        String extension = ".class";
        _class += extension;
        File file = FileService.getFile(this, _class);
        sendFileInfo(file);
        try {
            communication.sendFile(file);
            String s = communication.read();
            communication.write(method);
            communication.write(String.valueOf(a));
            communication.write(String.valueOf(b));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void sendObject(Object object) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void objectColl(String _class, String method, int a, int b) {
        try {
            communication.write(method);
            communication.read();
            communication.write(String.valueOf(a));
            communication.read();
            communication.write(String.valueOf(b));
            sendObject(FileService.getObject(_class));
            System.out.println("objet envoyer");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getAnswer() {
        String answer = "You have to ask the good questions";
        try {
            answer = communication.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return answer;
    }

    public Communication getCommunication() {
        return communication;
    }

}
