import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Optional;

public class Connexion implements Runnable {
    private Socket client;
    Communication communication;

    public Connexion(Socket socket) {
        client = socket;
        communication = new Communication(socket);
    }

    public Optional sourceColl(String _file, String method) {
        File file = FileService.compile(this, FileService.getFile(this, _file));
        return callMethod(file.getName(), method);
    }

//    public Optional byteColl(String file, String method) {
//        return callMethod(file, method);
//    }

    public Optional receiveObject(String method) {
        try {
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            Object o = ois.readObject();
            return callMethod(o, method);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * @param _class The class in plain text
     * @param _method the method to call
     * @return the result of the method
     */
    private Optional callMethod(String _class, String _method) {
        Object o = FileService.getObject(_class.substring(0, _class.indexOf(".")));
        return callMethod(o, _method);
    }

    /**
     * Right now, we supposed we know the number of parameters, their types and the type return by the method
     * @param o The object called
     * @param _method The method called
     * @return the result of the method
     */
    public Optional<String> callMethod(Object o, String _method) {
        Optional<String> optional;
        Method method = null;
        try {
            method = o.getClass().getMethod(_method, int.class, int.class);
            optional = Optional.of(String.valueOf((int) method.invoke(o, 1, 1)));
            return optional;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private String getFile() {
        try {
            String file = communication.read();
            String s = communication.read();
            int file_size = Integer.parseInt(s);
            communication.saveFile("serverFiles/clientFiles/" + file, file_size);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void sendResponse(Optional<String> optional) {
        String answer;
        answer = optional.orElseGet(Message::getEmptyResult);
        try {
            communication.write(answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void byteColl() {
        try {
            String file = getFile();
            communication.write(Message.ack());
            String method = communication.read();
            Optional result = callMethod(file, method);
            sendResponse(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sourceColl() {
        try {
            String _file = getFile();
            String method = communication.read();
            File file = FileService.compile(this, FileService.getFile(this, _file));
            Optional result = callMethod(file.getName(), method);
            sendResponse(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void objectColl() {
        try {
            String method = communication.read();
            communication.write(Message.ack());
            Optional result = receiveObject(method);
            sendResponse(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            String choosen = communication.read();
            if (choosen.charAt(0) == Message.getByteColl().charAt(0)) {
                communication.write(Message.ack());
                byteColl();
            } else if (choosen.charAt(0) == Message.getObjectColl().charAt(0)) {
                communication.write(Message.ack());
                objectColl();
            } else if (choosen.charAt(0) == Message.getSourceColl().charAt(0)) {
                communication.write(Message.ack());
                sourceColl();
            } else {
                communication.write(Message.getWrongChoice());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        testReceivedFile();
    }

    /**
     * TEST
     */


    public void testReceivedFile() {
        try {
            String string = "";
            string = communication.read();
            communication.write(Message.ack());
            getFile();
//            String file = communication.read();
//            String s = communication.read();
//            int file_size = Integer.parseInt(s);
//            communication.saveFile("serverFiles/clientFiles/" +file, file_size);
            communication.write(Message.ack());
//            string = communication.read();
//            communication.write("after fini");
//            communication.outputStreamWriter.write("ola\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
