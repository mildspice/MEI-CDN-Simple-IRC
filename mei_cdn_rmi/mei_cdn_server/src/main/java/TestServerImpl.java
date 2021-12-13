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
			System.out.println("RMI Server ready");
		}
		catch(RemoteException e) {
			e.printStackTrace();
		}

    //System.setSecurityManager(new RMISecurityManager());
    TestServerImpl dateS = new TestServerImpl();
    Naming.bind("rmi://127.0.0.1/TestServer", dateS);
  }
}
