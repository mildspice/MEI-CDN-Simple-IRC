import java.util.Date;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DummyInterface extends Remote {

    public Date getDate() throws RemoteException;

}