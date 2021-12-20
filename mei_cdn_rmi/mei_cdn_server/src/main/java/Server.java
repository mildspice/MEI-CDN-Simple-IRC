import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

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
    String host = "localhost", port = "1099";
		if (args.length == 2) {
			host = args[0];
      try {
        Integer.parseInt(port);
        port = args[1];
      } catch (NumberFormatException ignored) {
        System.out.println("> Invalid port. Using " + host + ":1099 as default ...");
      }
		} else if (args.length == 1) {
      host = args[0];
			System.out.println("> No port specified. Using " + host + ":1099 as default ...");
		} else {
			System.out.println("> No host or port specified. Using 'localhost:1099' as default ...");
		}

    // # not required in Java 14
    // System.setSecurityManager(new RMISecurityManager());
    startRmiRegistry(Integer.parseInt(port)); 

    UserManagement shared = new UserManagement();
    MessageHistory messageHistory = new MessageHistory();
    FileManagement fileManagement = new FileManagement();
    AuthServer auth = new AuthServer(shared, messageHistory);
    ChatServer chat = new ChatServer(shared, messageHistory, fileManagement);
    FileServer fileServer = new FileServer(fileManagement);
    Naming.bind("rmi://" + host + "/AuthServer", auth);
    System.out.println("> Successfully registered RMI interface - rmi://" + host + "/AuthServer");
    Naming.bind("rmi://" + host + "/ChatServer", chat);
    System.out.println("> Successfully registered RMI interface - rmi://" + host + "/ChatServer");
    Naming.bind("rmi://" + host + "/FileServer", fileServer);
    System.out.println("> Successfully registered RMI interface - rmi://" + host + "/FileServer");
  }

  // public static void main (String args[]) throws Exception {
  //   startRmiRegistry(1099);
    
  //   // # not required in Java 14
  //   // System.setSecurityManager(new RMISecurityManager());

  //   if (args.length >= 1) {
  //     boolean validHost = validHost(args[0]);
  //     String host = validHost ? args[0] : "localhost";

  //     for (int i = validHost ? 1 : 0; i < args.length; i++) {

  //       try {
  //         Remote dynamicRmiClass = (Remote) Class.forName(args[i]).getConstructor().newInstance();

  //         Naming.bind("rmi://" + host + "/" + args[i], dynamicRmiClass);
  //         System.out.println("> Successfully registered RMI interface - rmi://" + host + "/" + args[i]);
  //       } catch (Exception ignored) {
  //         System.out.println("> Could not find RMI interface - " + args[i]);
  //       }
  //     }

  //   } else {
  //     System.out.println("> No RMI interfaces were specified.\n> Server did not start." +
  //       "\n> Usage (localhost): java ... Server <rmi_interface> <rmi_interface ...>\n> Usage: java ... Server <host> <rmi_interface> <rmi_interface ...>");
  //   }
  // }

  // public static boolean validHost(String ip) {
  //   if (ip == null || ip.isEmpty()) {
  //       return false;
  //   }
  //   if ( ip.equals("localhost") ) {
  //     return true;
  //   }
  //   if (!Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$").matcher(ip).matches()) {
  //       return false;
  //   }
  //   String[] parts = ip.split("\\.");
  //   try {
  //       for (String segment: parts)
  //       {
  //           if (Integer.parseInt(segment) > 255 ||(segment.length() > 1 && segment.startsWith("0"))) {
  //               return false;
  //           }
  //       }
  //   } catch (NumberFormatException e) {
  //       return false;
  //   }
  //   return true;
  // }
}
