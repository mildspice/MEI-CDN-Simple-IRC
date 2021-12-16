import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserManagement {
    private final Map<String, User> users = new HashMap<>();

    public UserManagement() {}

    public synchronized void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public synchronized User removeUser(String username) {
        return users.remove(username);
    }

    public User getUser(String username) {
        return users.get(username);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    public Collection<String> getUserNames() {
        return users.keySet();
    }

    public void sendMessageToAll(String who, String message) {
        for (User user : users.values()) {
            try {
                user.getChatClientInterface().publicChatMessage(who, message); 
            } catch (RemoteException e) {
                System.err.println("--- ERR\n> Error updating online users to client - " + user.getUsername() + "\n---");
            }
        }
    }

    public void sendOnlineUsersUpdatedListToAll() {
        for (User user : users.values()) {
            try {
                user.getChatClientInterface().updateOnlineUsers(users.keySet()); 
            } catch (RemoteException e) {
                System.err.println("--- ERR\n> Error updating online users to client - " + user.getUsername() + "\n---");
            }
        }
    }
}
