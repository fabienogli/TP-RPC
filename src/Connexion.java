import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.nio.channels.Channels;
import java.util.Optional;

public class Connexion implements Runnable {
    private Socket client;
    private boolean active;

    public Connexion(Socket socket) {
        client = socket;
        active = true;
    }

    @Override
    public void run() {
        String recu = "";
//        try {
//            int filesize = Integer.parseInt(Communication.read(client));
//            receivedFile("received", filesize);
            objectColl("","");
//            recu = Communication.read(client);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println(recu);
    }

    public void receivedFile(String string, int filesize) {
        String file = string;
        try {
            Communication.saveFile(string, client, filesize);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sourceColl(String _file, String method) {
        File file = FileService.compile(FileService.getFile(_file));
        byteColl(file, method);
    }

    public void byteColl(String file, String method) {
        byteColl(FileService.getFile(file), method);
    }

    public void byteColl(File file, String method) {
        callMethod(file.getName(), method);
    }

    public void objectColl(String object, String method) {
        object = "Test";
        method = "add";
        try {
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            Object o = ois.readObject();
            callMethod(o, method);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param _class The class in plain text
     * @param _method the method to call
     * @return the result of the method
     */
    public Optional callMethod(String _class, String _method) {
        Object o = FileService.getObject(_class);
        return callMethod(o, _method);
    }

    /**
     * Right now, we supposed we know the number of parameters, their types and the type return by the method
     * @param o The object called
     * @param _method The method called
     * @return the result of the method
     */
    public Optional callMethod(Object o, String _method) {
        Optional optional;
        Method method = null;
        try {
            method = o.getClass().getMethod(_method, int.class, int.class);
            optional = Optional.of((int) method.invoke(o, 1, 1));
            System.out.println("Result=" + optional.toString());
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

    public static void main(String[] args) {

    }

    public static String fileToClass(File file) {
        String _class = file.getName();
        _class = _class.substring(0, _class.lastIndexOf('.'));
        return _class;
    }
}
