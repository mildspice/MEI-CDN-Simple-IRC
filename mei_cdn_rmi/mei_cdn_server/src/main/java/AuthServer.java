import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AuthServer extends UnicastRemoteObject implements AuthInterface {
    private SharedData shared;

    public AuthServer(SharedData shared) throws RemoteException {
        super();
        this.shared = shared;
    }

    public boolean login(String username, ChatClientInterface msgHandler) {
        if (shared.getUser(username) == null) {
            shared.addUser(new User(username, msgHandler));
            // send msg to all clients notifying the login
            return true;
        } else {
            return false;
        }
    }

    public boolean logout(String username) {
        if (shared.removeUser(username) != null) {
            for (User user : shared.getAllUsers()) {
                user.getChatClientInterface(); // update list of online users | .updateOnlineUsers(users.keySet());
            }
            return true;
        } else {
            return false;
        }
    }
}
