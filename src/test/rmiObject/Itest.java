package test.rmiObject;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Itest extends Remote {
    public String sayHello() throws RemoteException;
}
