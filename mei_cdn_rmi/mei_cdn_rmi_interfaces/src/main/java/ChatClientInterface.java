import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface ChatClientInterface extends Remote {
    
    void chatMessage(Message message) throws RemoteException;

    void updateOnlineUsers(Collection<String> userNames) throws RemoteException;
    
}
