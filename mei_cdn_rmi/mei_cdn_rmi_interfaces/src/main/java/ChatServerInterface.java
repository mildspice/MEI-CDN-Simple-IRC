import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatServerInterface extends Remote {
    
	public void updateChat(String userName, String chatMessage)throws RemoteException;

    public void sendPM(String senderUsername, List<String> userList, String privateMessage) throws RemoteException;

}
