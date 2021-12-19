import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {
    private UserManagement userManagement;

    protected ChatServer(UserManagement shared) throws RemoteException {
        super();
        this.userManagement = shared;
    }

    @Override
    public void updateChat(Message message) throws RemoteException {
        userManagement.sendMessageToAll(message);
    }

    @Override
    public void sendPM(List<String> userList, Message message) throws RemoteException {    
		for(String u : userList){
            User user = userManagement.getUser(u);
            user.getChatClientInterface().chatMessage(message);
		}

    }

}
