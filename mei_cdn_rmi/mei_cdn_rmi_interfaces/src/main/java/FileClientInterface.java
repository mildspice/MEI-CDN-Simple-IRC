import java.rmi.RemoteException;

public interface FileClientInterface {

    public byte[] uploadFileToServer(FileServerInterface fServer, String serverPath, String clientPath) throws RemoteException;
	public boolean downloadFileFromServer(FileServerInterface fServer, String serverPath, String clientPath) throws RemoteException;

}
