import java.rmi.RemoteException;

public interface FileClientInterface {

    public boolean uploadFileToServer(FileServerInterface fServer, String path) throws RemoteException;
	public byte[] downloadFileFromServer(FileServerInterface fServer, String path) throws RemoteException;

}
