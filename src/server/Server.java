package server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket socket;
    private static int _PORT = 2025;

    static Scanner scanner = new Scanner(System.in);

    public Server() throws IOException {
        this(_PORT);
    }

    public Server(int port) throws IOException {
        this.socket = new ServerSocket(port);
    }

    public Socket getClient() throws IOException {
        Socket socket = this.socket.accept();
        return socket;
    }
}
