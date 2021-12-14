import java.util.Date;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class TestServerImpl extends UnicastRemoteObject implements TestServer {
  public TestServerImpl() throws RemoteException {
  }
  public Date getDate() {
    return new Date();
  }
  public String getString() {
    return new String("It works !!!!!");
  }
  public static void main (String args[]) throws Exception {
    try{
			LocateRegistry.createRegistry(1099);
			System.out.println("RMI Server ready - listening on port 1099");
		}
		catch(RemoteException e) {
			e.printStackTrace();
		}

    String host = "localhost", rmi_class = "TestServer";
    if (args.length == 2) {
      host = args[0];
      rmi_class = args[1];
    }

    //System.setSecurityManager(new RMISecurityManager());
    TestServerImpl dateS = new TestServerImpl();
    Naming.bind("rmi://" + host + "/" + rmi_class, dateS);
  }
}
