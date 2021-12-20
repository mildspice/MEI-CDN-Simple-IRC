import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class FileClient extends UnicastRemoteObject implements FileClientInterface {

    protected FileClient() throws RemoteException {
        super();
    }

    public boolean downloadFileFromServer(FileServerInterface fServer, String serverPath, String clientPath)
            throws RemoteException {
        byte[] data = fServer.downloadFileFromServer(serverPath);
        if(data == null){
            System.out.println("File does not exist ばか");
            return false;
        }
        File clientpathfile = new File(clientPath);
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

    public byte[] uploadFileToServer(FileServerInterface fServer, String serverPath, String clientPath)
            throws RemoteException {
        File clientpathfile = new File(clientPath);
        serverPath = fileNameValidator(serverPath, clientpathfile.getName());
        byte[] data = new byte[(int) clientpathfile.length()];
        try {
            FileInputStream in = new FileInputStream(clientpathfile);
            in.read(data, 0, data.length);
            fServer.uploadFileToServer(data, serverPath, (int) clientpathfile.length());
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return data;
    }

    public List<String> listAllFiles(FileServerInterface fServer) throws RemoteException{
        return fServer.listAllFiles();
    }

    private String fileNameValidator(String serverfPath, String fileName) {
        if (serverfPath.isBlank() || serverfPath.isEmpty()) {
            return fileName;
        } else {
            return serverfPath;
        }
    }

}
