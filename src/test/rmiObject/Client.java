package test.rmiObject;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public Client() {

    }

    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        Registry registry;
        try {
            registry = LocateRegistry.getRegistry(host);
            Server stub = (Server) registry.lookup("Hello");
            String response = stub.sayHello();
            System.out.println("response " +response);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
