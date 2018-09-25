import java.io.*;
import java.net.Socket;

public class Communication {

    public static void write(Socket socket, String message) throws IOException {
        DataOutputStream stream = new DataOutputStream(socket.getOutputStream());
        if (!message.endsWith("\n")) {
            message += "\n";
        }
        stream.writeBytes(message);
    }

    public static String read(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String result = reader.readLine();
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
        fis.close();
        dos.flush();
    }

    public static void saveFile(String file, Socket client, int filesize) throws IOException {
        DataInputStream dis = new DataInputStream(client.getInputStream());
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[4096];

        int read = 0;
        int totalRead = 0;
        int remaining = filesize;
        while((read = dis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            fos.write(buffer, 0, read);
        }
        fos.close();
    }


}
