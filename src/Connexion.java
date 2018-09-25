import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

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
        try {
            recu = Communication.read(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(recu);
    }

    public void saveFile(String file, int filesize,  Socket client) throws IOException {
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
        dis.close();
    }
}
