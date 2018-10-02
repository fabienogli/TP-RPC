package test;
import client.Client;
import util.Communication;
import util.Message;

import java.io.IOException;

public class TestClient {


    public static void main(String[] args) {
        int a =2;
        int b = 4;
        testByteColl(a, b);
        testObjectColl(a, b);
        testSourceColl(a, b);
    }

    public static String testObjectColl(int a, int b) {
        Client client = null;
        try {
            client = new Client();
            Communication communication = client.getCommunication();
            try {
                communication.write(Message.getObjectColl());
                communication.read();
                String _class = "Test";
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
                String _class = "Test";
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
                String _class = "Test";
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

//    public void testSendFile() {
//        File file = util.FileService.getFile(this, "Test");
//        try {
//            communication.write("OLA");
//            communication.read();
//            sendFileInfo(file);
//            communication.sendFile(file);
//            String s = communication.read();
////            System.out.println(s);
////            communication.write("fini");
////            System.out.println(communication.reader.ready());
////            communication.read();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) {
//        client.Client client = null;
//        File file = new File("/home/fabien/github/systemes_reparties_tp1/src/clientFiles/Test.java");
//        File file2 = new File("/home/fabien/github/systemes_reparties_tp1/src/clientFiles/Test2.java");
//        util.FileService.compile(client, file);
//        util.FileService.compile(client, file2);
//
//    }
}
