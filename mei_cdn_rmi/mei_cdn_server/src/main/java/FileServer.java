import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class FileServer extends UnicastRemoteObject implements FileServerInterface {

    private FileManagement fileManager;

    protected FileServer(FileManagement fileManagement) throws RemoteException {
        super();
        this.fileManager = fileManagement;
    }

    public boolean uploadFileToServer(byte[] data, String serverPath, int length) throws RemoteException {
        try {
            System.out.println("> Saving File: " + serverPath);
            File serverPathFile = new File(serverPath);
            FileOutputStream out = new FileOutputStream(serverPathFile);
            byte[] bdata = data;
            out.write(bdata);
            out.flush();
            out.close();
            fileManager.addFile(serverPath);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public byte[] downloadFileFromServer(String serverPath) throws RemoteException {
        if (fileManager.lookupFile(serverPath)) {
            byte[] data;
            File serverpathfile = new File(serverPath);
            data = new byte[(int) serverpathfile.length()];
            FileInputStream in;
            try {
                in = new FileInputStream(serverpathfile);
                in.read(data, 0, data.length);
                in.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        } else {
            return null;
        }
    }

    @Override
    public List<String> listAllFiles() throws RemoteException {
        return fileManager.listFiles();
    }

}
