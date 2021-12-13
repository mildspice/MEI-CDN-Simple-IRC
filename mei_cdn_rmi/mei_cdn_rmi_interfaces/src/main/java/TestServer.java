

import java.util.Date;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TestServer extends Remote {

    public Date getDate() throws RemoteException;
    public String getString() throws RemoteException;

}