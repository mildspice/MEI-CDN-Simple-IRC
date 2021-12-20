import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

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

        //ChatClient chat = new ChatClient();
        //UnicastRemoteObject.exportObject(chat, 1099);
        FileServerInterface files = (FileServerInterface) Naming.lookup("rmi://" + host + "/FileServer");
        System.out.println("> Successfully connected to RMI registry: rmi://" + host + "/FileServer");

        //ClientGUI.openChatMainWindow();
        UIManager.setLookAndFeel(new NimbusLookAndFeel());
        SwingUtilities.invokeLater(new Runnable() {

          @Override
          public void run() {
            System.out.println("> Opening GUI ...");
            ClientGUI.openChatMainWindow(auth, files);
          }
        });

      } catch (NotBoundException | MalformedURLException err) {
        System.out.println("> Server seems to be unavailable. NOT FOUND | rmi://" + host + "/AuthServer");
        err.printStackTrace();
      } catch (UnsupportedLookAndFeelException exc) {
        System.err.println("Nimbus: Unsupported Look and feel!");
      }
    }
}
