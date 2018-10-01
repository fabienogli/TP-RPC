import java.io.*;
import java.net.Socket;

public class Communication {

    public static void write(Socket socket, String message) throws IOException {
        DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
        message += "\n";
        stream.writeBytes(message);
    }

    public static String read(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String result = reader.readLine();
        System.out.println("read: " +result);
        return result;
    }

    public static void sendObject(Socket socket, Object object) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The customer will send his file to the server
     * @param file
     * @param socket
     * @throws IOException
     */
    public static void sendFile(File file, Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        while (fis.read(buffer) > 0) {
            dos.write(buffer);
        }
        dos.flush();
        fis.close();
    }




}
