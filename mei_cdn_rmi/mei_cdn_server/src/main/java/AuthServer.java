import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class AuthServer extends UnicastRemoteObject implements AuthRmiInterface {
    protected AuthServer() throws RemoteException {
        super();
    }

    public boolean login(String username, String password) {
        return false;
    }

    public boolean register(String username, String password, String email) {
        return false;
    }
}
