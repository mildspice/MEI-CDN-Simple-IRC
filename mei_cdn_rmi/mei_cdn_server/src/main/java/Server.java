import java.util.Date;
import java.util.regex.Pattern;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements DummyInterface {

  public Server() throws RemoteException {
    super();
  }

  public Date getDate() {
    return new Date();
  }

  private static void startRmiRegistry(int port) {
    try {
      System.out.println("> Loading RMI registry ...");
			LocateRegistry.createRegistry(1099);
			System.out.println("> RMI Server ready! | listening on port " + port);
		} catch(RemoteException e) {
      System.err.println("> Error loading RMI registry!\n---\n" + e);
		}
  }
 
  public static void main (String args[]) throws Exception {
    startRmiRegistry(1099);
    
    // # not required in Java 14
    // System.setSecurityManager(new RMISecurityManager());
    AuthServer auth = new AuthServer();

    if (args.length >= 2) {
      String host = validHost(args[0]) ? args[0] : "localhost";

      for (int i = 1; i < args.length; i++) {
        Naming.bind("rmi://" + host + "/" + args[i], auth);
        System.out.println("> Successfully registered RMI interface - rmi://" + host + "/" + args[i]);
      }

    } else {
      System.out.println("> No RMI interfaces were specified.\n> Server did not start." +
        "\n> Usage (localhost): java ... Server <rmi_interface> <rmi_interface ...>\n> Usage: java ... Server <host> <rmi_interface> <rmi_interface ...>");
    }
  }

  public static boolean validHost(String ip) {
    if (ip == null || ip.isEmpty()) {
        return false;
    }
    if ( ip.equals("localhost") ) {
      return true;
    }
    if (!Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$").matcher(ip).matches()) {
        return false;
    }
    String[] parts = ip.split("\\.");
    try {
        for (String segment: parts)
        {
            if (Integer.parseInt(segment) > 255 ||(segment.length() > 1 && segment.startsWith("0"))) {
                return false;
            }
        }
    } catch (NumberFormatException e) {
        return false;
    }
    return true;
  }
}
