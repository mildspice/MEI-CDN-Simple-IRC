import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AuthServer extends UnicastRemoteObject implements AuthInterface {
    private UserManagement shared;

    public AuthServer(UserManagement shared) throws RemoteException {
        super();
        this.shared = shared;
    }

    public boolean login(String username, ChatClientInterface msgHandler) {
        if (shared.getUser(username) == null) {
            shared.addUser(new User(username, msgHandler));
            shared.sendMessageToAll("Server", username + " as logged into the general chat. Say hi!");
            return true;
        } else {
            return false;
        }
    }

    public boolean logout(String username) {
        if (shared.removeUser(username) != null) {
            shared.sendOnlineUsersUpdatedListToAll(); // update list of online users
            return true;
        } else {
            return false;
        }
    }
}
