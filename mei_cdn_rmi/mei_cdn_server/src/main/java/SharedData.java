import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SharedData {
    private final Map<String, User> users = new HashMap<>();

    public SharedData() {

    }

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
}
