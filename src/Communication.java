import java.io.*;
import java.net.Socket;

public class Communication {
    Socket socket;
    BufferedReader reader;
    PrintWriter bufferedWriter;
    OutputStreamWriter outputStreamWriter;

    public Communication(Socket socket){
        this.socket = socket;
        try {
            outputStreamWriter =new OutputStreamWriter(socket.getOutputStream());
            bufferedWriter = new PrintWriter(outputStreamWriter);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String message) {
        System.out.println("write:" +message);
        bufferedWriter.println(message);
        bufferedWriter.flush();
    }

    public String read() throws IOException {
        String result = reader.readLine();
        System.out.println("read: " +result);
        return result;
    }

    /**
     * The customer will send his file to the server
     * @param file
     * @throws IOException
     */
    public boolean sendFile(File file) throws IOException {
        System.out.println(file.getAbsolutePath());
        System.out.println("Debut Sendfile");
        DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        Long l = file.length();
        byte[] buffer = new byte[4096];
        read();
        while (fis.read(buffer) > 0) {
            dos.write(buffer, 0, l.intValue());
//            dos.flush();
        }
        dos.flush();
        fis.close();
        read();
        System.out.println("Fin Sendfile");
        return true;
    }

    public void saveFile(String file, int filesize) throws IOException {
        System.out.println(file);
        System.out.println("Save File Debut");
        DataInputStream dis = new DataInputStream(this.socket.getInputStream());
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[4096];

        int totalRead = 0;
        int remaining = filesize;
        write(Message.ack());
        int read = dis.read(buffer, 0, Math.min(buffer.length, remaining));
        System.out.println("remaining:" + remaining);
        while(read > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("dans la boucle while:" + read);
            fos.write(buffer, 0, read);
            fos.flush();
            read = dis.read(buffer, totalRead, Math.min(buffer.length, remaining));
        }
        fos.flush();
        fos.close();
        write(Message.ack());
        System.out.println("Save File Fin");
    }







}
