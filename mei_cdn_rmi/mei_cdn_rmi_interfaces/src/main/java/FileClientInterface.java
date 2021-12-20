import java.rmi.RemoteException;
import java.util.List;

public interface FileClientInterface {

    public boolean uploadFileToServer(FileServerInterface fServer, String serverPath, String clientPath) throws RemoteException;
	public boolean downloadFileFromServer(FileServerInterface fServer, String serverPath, String clientPath) throws RemoteException;
    public List<String> listAllFiles(FileServerInterface fServer) throws RemoteException;

}
