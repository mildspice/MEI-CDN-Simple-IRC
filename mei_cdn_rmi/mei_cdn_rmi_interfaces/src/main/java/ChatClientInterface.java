import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface ChatClientInterface extends Remote {
    
    void chatMessage(String username, String message) throws RemoteException;

    void updateOnlineUsers(Collection<String> userNames) throws RemoteException;
    
}
