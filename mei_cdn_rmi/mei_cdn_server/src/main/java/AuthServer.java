import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AuthServer extends UnicastRemoteObject implements AuthInterface {
    private UserManagement userManagement;

    public AuthServer(UserManagement shared) throws RemoteException {
        super();
        this.userManagement = shared;
    }

    public boolean login(String username, ChatClientInterface msgHandler) {
        if (userManagement.getUser(username) == null) {
            userManagement.addUser(new User(username, msgHandler));
            // userManagement.sendMessageToAll("Server", username + " as logged into the general chat. Say hi!"); DEPRECATED
            userManagement.sendOnlineUsersUpdatedListToAll();
            return true;
        } else {
            return false;
        }
    }

    public boolean logout(String username) {
        if (userManagement.removeUser(username) != null) {
            userManagement.sendOnlineUsersUpdatedListToAll(); // update list of online users
            return true;
        } else {
            return false;
        }
    }
}
