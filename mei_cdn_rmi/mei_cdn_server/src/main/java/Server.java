import java.util.Date;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements DummyInterface {

  public Server() throws RemoteException {
  }

  public Date getDate() {
    return new Date();
  }

  private static void startRmiRegistry(int port) {
    try {
			LocateRegistry.createRegistry(1099);
			System.out.println("RMI Server ready - listening on port " + port);
		} catch(RemoteException e) {
      System.err.println("Error loading RMI registry!\n---\n" + e);
		}
  }
 
  public static void main (String args[]) throws Exception {
    startRmiRegistry(1099);
    
    String host = "localhost", rmi_class = "DummyInterface";
    if (args.length == 2) {
      host = args[0];
      rmi_class = args[1];
    }

    // # not required in Java 14
    // System.setSecurityManager(new RMISecurityManager());
    Server dateS = new Server();
    Naming.bind("rmi://" + host + "/" + rmi_class, dateS);
    System.out.println("RMI URL: " + "rmi://" + host + "/" + rmi_class);
  }
}
