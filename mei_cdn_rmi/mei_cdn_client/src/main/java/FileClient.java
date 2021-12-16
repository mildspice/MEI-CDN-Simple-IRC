import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileClient extends UnicastRemoteObject implements FileClientInterface{


    protected FileClient() throws RemoteException {
        super();

    }

    public byte[] downloadFileFromServer(FileServerInterface fServer, String path) throws RemoteException {

        return null;
    }

    public boolean uploadFileToServer(FileServerInterface fServer, String path) throws RemoteException {
        try{
        File clientpathfile = new File(path);
        byte [] mydata=new byte[(int) clientpathfile.length()];
        FileInputStream in=new FileInputStream(clientpathfile);		
         in.read(mydata, 0, mydata.length);					 
         fServer.uploadFileToServer(mydata, path, (int) clientpathfile.length());        
         in.close();
        }catch (FileNotFoundException e) {     
            e.printStackTrace();
        }	
        catch (IOException e) {        
            e.printStackTrace();
        }	
        return false;
    }
    
}
