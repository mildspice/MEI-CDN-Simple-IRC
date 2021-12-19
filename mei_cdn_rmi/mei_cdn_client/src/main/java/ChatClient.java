import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collection;

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {

    protected ChatClient() throws RemoteException {
        super();
    }

    @Override
    public void chatMessage(String username, String message, boolean isPrivate) throws RemoteException {
        String parsedMessage;
        if (isPrivate){
            parsedMessage = "PM from " + username + ": " + message;
        }else{
            parsedMessage = username + ": " + message;
        }
        System.out.println(parsedMessage);
    }

    @Override
    public void updateOnlineUsers(Collection<String> userNames) throws RemoteException {
        // TODO Auto-generated method
    }
    
}
