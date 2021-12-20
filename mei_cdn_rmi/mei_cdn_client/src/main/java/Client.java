import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import javax.swing.SwingUtilities;

public class Client {

    public static void main (String args[]) throws Exception {
      String host = "localhost";
      if (args.length == 1) {
        host = args[0];
      } else {
        System.out.println("> No host was specified. Using 'localhost' as default ...");
      }

      try {		
        AuthInterface auth = (AuthInterface) Naming.lookup("rmi://" + host + "/AuthServer");
        System.out.println("> Successfully connected to RMI registry: rmi://" + host + "/AuthServer");
		ChatServerInterface chatServer  = (ChatServerInterface) Naming.lookup("rmi://" + host + "/ChatServer");
        System.out.println("> Successfully connected to RMI registry: rmi://" + host + "/ChatServer");
        //ChatClient chat = new ChatClient();
        //UnicastRemoteObject.exportObject(chat, 1099);
        FileServerInterface fileServer = (FileServerInterface) Naming.lookup("rmi://" + host + "/FileServer");
        System.out.println("> Successfully connected to RMI registry: rmi://" + host + "/FileServer");

        FileClient fileClient = new FileClient();
		
        //ClientGUI.openChatMainWindow();
        SwingUtilities.invokeLater(new Runnable() {

          @Override
          public void run() {
            System.out.print("> Opening GUI ...");
            ClientGUI.openChatMainWindow(auth);
          }
        });

      } catch (NotBoundException | MalformedURLException err) {
        System.out.println("> Server seems to be unavailable. NOT FOUND | rmi://" + host + "/AuthServer");
        err.printStackTrace();
      }
    }
}
