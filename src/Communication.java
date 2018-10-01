import java.io.*;
import java.net.Socket;

public class Communication {
    PrintWriter printWriter;
    Socket socket;
    BufferedReader reader;
    BufferedWriter bufferedWriter;

    public Communication(Socket socket){
        this.socket = socket;
        try {
            printWriter = new PrintWriter(socket.getOutputStream());
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String message) throws IOException {
//        printWriter.println(message);
        System.out.println("write:" +message);
        bufferedWriter.append(message)
                .append("\n");
        bufferedWriter.flush();
//        printWriter.flush();
    }

    public String read() throws IOException {
        String result = reader.readLine();
//        reader.lines().forEach(s -> System.out.println(s));
        System.out.println("read: " +result);
        return result;
    }

    public void sendObject(Object object) {
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
     * @throws IOException
     */
    public void sendFile(File file) throws IOException {
        System.out.println("Debut Sendfile");
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[4096];
        while (fis.read(buffer) > 0) {
//            System.out.println(buffer);
            dos.write(buffer);
        }
        dos.flush();
        fis.close();
        write("");
        System.out.println("Fin Sendfile");
    }




}
