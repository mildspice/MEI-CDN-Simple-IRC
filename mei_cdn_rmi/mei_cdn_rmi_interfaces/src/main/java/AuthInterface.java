import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AuthInterface extends Remote {
    
    boolean login(String username, ChatClientInterface clientMsgHandler) throws RemoteException;

    boolean logout(String username) throws RemoteException;
}
