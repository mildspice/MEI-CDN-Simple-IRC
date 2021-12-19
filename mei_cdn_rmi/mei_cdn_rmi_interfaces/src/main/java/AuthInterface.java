import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AuthInterface extends Remote {
    
    boolean login(String username, ChatClientInterface clientMsgHandler) throws RemoteException;

    List<String> getMessageHistory() throws RemoteException;

    boolean logout(String username) throws RemoteException;
}
