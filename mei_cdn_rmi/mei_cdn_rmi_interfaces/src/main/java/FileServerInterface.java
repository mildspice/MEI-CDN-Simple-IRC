import java.rmi.*;
import java.util.List;

public interface FileServerInterface extends Remote {
	
	public boolean uploadFileToServer(byte[] data, String serverPath, int length) throws RemoteException;
	public byte[] downloadFileFromServer(String serverPath) throws RemoteException;
	public List<String> listAllFiles() throws RemoteException;
}