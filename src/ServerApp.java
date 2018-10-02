import server.Connexion;
import server.Server;

import java.io.IOException;
import java.net.Socket;

public class ServerApp {

    public static void main(String[] args) {
        Server server;
        try {
            server = new Server();
            System.out.println("Le serveur est lancé et écoute 127.0.0.1:2025 ");
        } catch (IOException e) {
            System.out.println("Problème dans instanciation de server");
            e.printStackTrace();
            return;
        }
        try {
            while (true){
                Socket socket = server.getClient();
                Thread thread = new Thread(new Connexion(socket));
                thread.run();
            }
        } catch (IOException e) {
            System.out.println("Problème pour récuperer le client");
            //e.printStackTrace();
        }
    }
}
