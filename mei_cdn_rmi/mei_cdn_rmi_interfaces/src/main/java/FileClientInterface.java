import java.rmi.RemoteException;

public interface FileClientInterface {

    public byte[] uploadFileToServer(FileServerInterface fServer, String path) throws RemoteException;
	public boolean downloadFileFromServer(FileServerInterface fServer, String path) throws RemoteException;

}
