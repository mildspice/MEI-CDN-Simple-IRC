import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class FileServer extends UnicastRemoteObject implements FileServerInterface{

    protected FileServer() throws RemoteException {
        super();
        //TODO Auto-generated constructor stub
    }

    public boolean uploadFileToServer(byte[] data, String serverPath, int length) throws RemoteException {
    	try {
    		File serverPathFile = new File(serverPath);
    		FileOutputStream out=new FileOutputStream(serverPathFile);
    		byte [] bdata=data;			
    		out.write(bdata);
			out.flush();
	    	out.close();
		} catch (IOException e) {			
			e.printStackTrace();
            return false;
		}
        return true;
    }

    public byte[] downloadFileFromServer(String serverPath) throws RemoteException {
        byte [] data;			
        File serverpathfile = new File(serverPath);
        data=new byte[(int) serverpathfile.length()];
        FileInputStream in;
        try {
            in = new FileInputStream(serverpathfile);
            in.read(data, 0, data.length);
            in.close();
        } catch (FileNotFoundException e) {     
            e.printStackTrace();
        }	
        catch (IOException e) {        
            e.printStackTrace();
        }	
        return data;
    }
    
}
