import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.MessageDigest;
import java.util.List;

public class ChatServer extends UnicastRemoteObject implements ChatServerInterface {
    private UserManagement userManagement;
    private MessageHistory messageHistory;
    private FileManagement fileManager;

    protected ChatServer(UserManagement shared, MessageHistory messageHistory, FileManagement fileManager)
            throws RemoteException {
        super();
        this.userManagement = shared;
        this.messageHistory = messageHistory;
        this.fileManager = fileManager;
    }

    @Override
    public void updateChat(Message message) throws RemoteException {
        if ((message.hasFile() && fileManager.lookupFile(message.getFileName()) || !message.hasFile())) {
            userManagement.sendMessageToAll(message);
            addHistoryToAllUsers(message);
        }
    }

    @Override
    public void sendPM(List<String> userList, Message message) throws RemoteException {
        if (message.hasFile() && fileManager.lookupFile(message.getFileName())) {
            for (String u : userList) {
                User user = userManagement.getUser(u);
                user.getChatClientInterface().chatMessage(message);
            }
        }
    }

    private void addHistoryToAllUsers(Message message) {
        for (User user : userManagement.getAllUsers()) {
            messageHistory.addUserMessage(user.getUsername(), message);
        }
    }

}
