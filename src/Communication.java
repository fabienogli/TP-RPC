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


}
