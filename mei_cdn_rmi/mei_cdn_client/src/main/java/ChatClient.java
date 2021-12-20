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
        if (message.isPM()) {
            ClientGUI.appendToChatBoardPanel("PM> " + message.getSender() + ": ", Color.yellow);
            
        } else {
            ClientGUI.appendToChatBoardPanel("GENERAL> " + message.getSender() + ": ", Color.blue);
        }
        ClientGUI.appendToChatBoardPanel(message.getMessage() + " ", Color.black);
        if (message.hasFile()) {
            ClientGUI.appendLinkToChatBoardPanel(message.getClientFile().getAbsolutePath(), Color.black);
        }
        ClientGUI.appendToChatBoardPanel("\n", Color.black);
    }

    @Override
    public void updateOnlineUsers(Collection<String> userNames) throws RemoteException {
        ClientGUI.onlineUsersPanel(new ArrayList<>(userNames));
    }
    
}
