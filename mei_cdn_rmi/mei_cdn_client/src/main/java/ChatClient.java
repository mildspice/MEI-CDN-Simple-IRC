import java.awt.Color;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;

public class ChatClient extends UnicastRemoteObject implements ChatClientInterface {

    protected ChatClient() throws RemoteException {
        super();
    }

    @Override
    public void chatMessage(Message message) throws RemoteException {
        ClientGUI.appendToChatBoardPanel("GENERAL", Color.blue);
        ClientGUI.appendToChatBoardPanel(" | ", Color.black);
        ClientGUI.appendToChatBoardPanel(message.getSender(), Color.blue);
        ClientGUI.appendToChatBoardPanel(" " + message, Color.black);
    }

    @Override
    public void updateOnlineUsers(Collection<String> userNames) throws RemoteException {
        ClientGUI.onlineUsersPanel(new ArrayList<>(userNames));
    }
    
}
