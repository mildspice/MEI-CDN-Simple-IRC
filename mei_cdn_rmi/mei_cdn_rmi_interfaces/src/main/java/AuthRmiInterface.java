import java.rmi.Remote;

public interface AuthRmiInterface extends Remote {
    
    boolean login(String username, String password);

    boolean register(String username, String password, String email);
}
