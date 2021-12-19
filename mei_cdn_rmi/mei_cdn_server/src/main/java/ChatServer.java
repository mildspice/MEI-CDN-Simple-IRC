import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {
    private UserManagement userManagement;
    private MessageHistory messageHistory;
    protected ChatServer(UserManagement shared, MessageHistory messageHistory) throws RemoteException {
        super();
        this.userManagement = shared;
        this.messageHistory = messageHistory;
    }

    @Override
    public void updateChat(Message message) throws RemoteException {
        userManagement.sendMessageToAll(message);
        addHistoryToAllUsers(message);
    }

    @Override
    public void sendPM(List<String> userList, Message message) throws RemoteException {    
		for(String u : userList){
            User user = userManagement.getUser(u);
            user.getChatClientInterface().chatMessage(message);
		}

    }

    private void addHistoryToAllUsers(Message message){
        for(User user : userManagement.getAllUsers()){
            messageHistory.addUserMessage(user.getUsername(), message);
        }
    }

}
