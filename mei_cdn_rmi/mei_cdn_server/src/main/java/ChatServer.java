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
    public void updateChat(String userName, String chatMessage) throws RemoteException {
        userManagement.sendMessageToAll(userName, chatMessage);
    }

    @Override
    public void sendPM(String senderUsername, List<String> userList, String privateMessage) throws RemoteException {    
		for(String u : userList){
            User user = userManagement.getUser(u);
            user.getChatClientInterface().chatMessage(senderUsername, privateMessage, true);
		}

    }

}
