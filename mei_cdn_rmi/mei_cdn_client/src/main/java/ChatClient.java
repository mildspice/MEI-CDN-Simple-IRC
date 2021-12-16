import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {

    protected ChatClient() throws RemoteException {
        super();
    }

    @Override
    public void publicChatMessage(String username, String message) throws RemoteException {
        // TODO Auto-generated method
    }

    @Override
    public void updateOnlineUsers(Collection<String> userNames) throws RemoteException {
        // TODO Auto-generated method
    }
    
}
