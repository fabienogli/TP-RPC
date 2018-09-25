package test;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements Itest {

    public Server() {

    }

    public String sayHello() {
        return "Hello, world!";
    }

    public static void main(String args[]) {
        Server server = new Server();
        try {
            System.out.println("ok");
            Itest remote = (Itest) UnicastRemoteObject.exportObject(server, 10000);
            System.out.println("ok");

            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", remote);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }
    }
}
