import java.io.*;
import java.net.Socket;

public class Communication {
    PrintWriter printWriter;

    public Communication(Socket socket){
        try {
            printWriter = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(Socket socket, String message) throws IOException {
        printWriter.println(message);
        System.out.println("write:" +message);
        printWriter.flush();
    }

    public String read(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String result = reader.readLine();
        System.out.println("read: " +result);
        return result;
    }

    public void sendObject(Socket socket, Object object) {
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
    public void sendFile(File file, Socket socket) throws IOException {
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        while (fis.read(buffer) > 0) {
            System.out.println(buffer);
            dos.write(buffer);
        }
        System.out.println("ici");
//        dos.flush();
        fis.close();
    }




}
