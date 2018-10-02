package test;
import client.Client;
import util.Communication;
import util.FileService;
import util.Message;

import java.io.IOException;

public class TestClient {


    public static void main(String[] args) {
        int a =2;
        int b = 4;
        System.out.println("ByteColl: " + testByteColl(a, b));
        System.out.println("ObjectColl: " + testObjectColl(a, b));
        System.out.println("SourceColl: " + testSourceColl(a, b));

    }

    public static String testObjectColl(int a, int b) {
        Client client = null;
        try {
            client = new Client();
            Communication communication = client.getCommunication();
            try {
                communication.write(Message.getObjectColl());
                communication.read();
                String _class = "SimpleCalc";
                String method = "add";
                client.objectColl(_class, method, a, b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = client.getAnswer();
        try {
            client.quit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String testByteColl(int a, int b) {
        Client client = null;
        try {
            client = new Client();
            Communication communication = client.getCommunication();
            try {
                communication.write(Message.getByteColl());
                communication.read();
                String _class = "SimpleCalc";
                String method = "add";
                client.byteColl(_class, method, a, b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = client.getAnswer();
        try {
            client.quit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String testSourceColl(int a, int b) {
        Client client = null;
        try {
            client = new Client();
            Communication communication = client.getCommunication();
            try {
                communication.write(Message.getSourceColl());
                communication.read();
                String _class = "SimpleCalc";
                String method = "add";
                client.sourceColl(_class, method, a, b);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response = client.getAnswer();
        try {
            client.quit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
