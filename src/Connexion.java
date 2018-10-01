import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Optional;

public class Connexion implements Runnable {
    private Socket client;
    private boolean active;

    public Connexion(Socket socket) {
        client = socket;
        active = true;
    }



    public Optional sourceColl(String _file, String method) {
        File file = FileService.compile(this, FileService.getFile(this, _file));
        return callMethod(file.getName(), method);
    }

    public Optional byteColl(String file, String method) {
        return callMethod(file, method);
    }

    public Optional objectColl(String method) {
        try {
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            Object o = ois.readObject();
//            System.out.println(o);
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
//        System.out.println("Method:" +_method);
        try {
            method = o.getClass().getMethod(_method, int.class, int.class);
            optional = Optional.of(String.valueOf((int) method.invoke(o, 1, 1)));
//            System.out.println("Result=" + optional.get());
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

    private String getFile() throws IOException {
//        System.out.println("Dans file info");
        String file = Communication.read(client);
        String size = Communication.read(client);
        int file_size = Integer.parseInt(size);
        saveFile(client, "serverFiles/clientFiles/" +file, file_size);
        return file;
    }

//    public void saveFile(Socket client, String file, int filesize) throws IOException {
//        DataInputStream dis = new DataInputStream(client.getInputStream());
//        FileOutputStream fos = new FileOutputStream(file);
//        byte[] buffer = new byte[4096];
//
//        int read = 0;
//        int totalRead = 0;
//        int remaining = filesize;
//        while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
//            System.out.println("boucle infin");
//            totalRead += read;
//            remaining -= read;
////            System.out.println("read " + totalRead + " bytes.");
//            fos.write(buffer, 0, read);
//        }
//        fos.close();
//    }

    private void saveFile(Socket clientSock, String file, int filesize) throws IOException {
        DataInputStream dis = new DataInputStream(clientSock.getInputStream());
        FileOutputStream fos = new FileOutputStream("testfile.jpg");
        byte[] buffer = new byte[4096];

        int read = 0;
        int totalRead = 0;
        int remaining = filesize;
        while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
//            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
        }

        fos.close();
        dis.close();
    }

    public byte[] readFile(String fileName) throws IOException {
        File file = new File(fileName);
        byte[] fileData = new byte[(int)file.length()];
        BufferedInputStream br = new BufferedInputStream(new FileInputStream(file));

        int bytesRead = 0;
        int b;
        while ((b = br.read()) != -1) {
            fileData[bytesRead++] = (byte)b;
        }

        br.close();
        return fileData;
    }

    private void sendFile(String filename) {
        for (byte i : data) {
            this.socket.getOutputStream().write(i);
        }

        System.out.println("\r\nSent " + data.length + " bytes to server.");
    }




    private void sendResponse(Optional<String> optional) {
        String answer;
        answer = optional.orElseGet(Message::getEmptyResult);
        try {
            Communication.write(client, answer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        /**
         * TEST
         */
//        testObjectColl();
        testByteColl();
//        testReceivedFile();
    }

    /**
     * TEST
     */

    private void testByteColl() {
        try {
            String file = getFile();

//             Communication.write(client, Message.ack());
             String method = Communication.read(client);
//            System.out.println("Method recu:" + method);
//             Optional result = byteColl(file, method);
//             sendResponse(result);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void testSourceColl() {
        try {
            String file = getFile();
            String method = Communication.read(client);
            Optional result = sourceColl(file, method);
            sendResponse(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void testObjectColl() {
        try {
            String method = Communication.read(client);
            Communication.write(client, Message.ack());
//            System.out.println(method);
            Optional result = objectColl(method);
            sendResponse(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testReceivedFile() {
        try {
            getFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
