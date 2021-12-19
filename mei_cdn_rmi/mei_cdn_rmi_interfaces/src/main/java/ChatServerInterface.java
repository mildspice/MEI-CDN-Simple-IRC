import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatServerInterface extends Remote {
    
	public void updateChat(Message message)throws RemoteException;

    public void sendPM(List<String> userList, Message message) throws RemoteException;

}
