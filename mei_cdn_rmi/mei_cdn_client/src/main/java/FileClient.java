import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileClient extends UnicastRemoteObject implements FileClientInterface{


    protected FileClient() throws RemoteException {
        super();

    }

    public boolean downloadFileFromServer(FileServerInterface fServer, String path) throws RemoteException {
        byte [] data = fServer.downloadFileFromServer(path);
        File clientpathfile = new File(path);
        FileOutputStream out;
        try {
            out = new FileOutputStream(clientpathfile);
            out.write(data);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }				
       
        return true;
    }

    public byte[] uploadFileToServer(FileServerInterface fServer, String path) throws RemoteException {
        File clientpathfile = new File(path);
        byte [] data=new byte[(int) clientpathfile.length()];
        try{
        FileInputStream in=new FileInputStream(clientpathfile);		
         in.read(data, 0, data.length);					 
         fServer.uploadFileToServer(data, path, (int) clientpathfile.length());        
         in.close();
        }catch (FileNotFoundException e) {     
            e.printStackTrace();
            return null;
        }	
        catch (IOException e) {        
            e.printStackTrace();
            return null;
        }	
        return data;
    }
    
}
