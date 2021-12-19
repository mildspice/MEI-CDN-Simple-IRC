import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;

public interface ChatClientInterface extends Remote {
    
    void chatMessage(String username, String message, boolean isPrivate) throws RemoteException;

    void updateOnlineUsers(Collection<String> userNames) throws RemoteException;

    // receção de ficheiro
    // ...
}
