package estg.ipp.pt.mei_cdn_rmi;

import java.util.Date;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.RMISecurityManager;
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
    if (!(args.length == 1 && args[0].equals("1"))) {
      try{
        java.rmi.registry.LocateRegistry.createRegistry(1099);
        System.out.println("RMI Server ready");
      }
      catch(RemoteException e) {
        e.printStackTrace();
      }
    }

    System.setSecurityManager(new RMISecurityManager());
    TestServerImpl dateS = new TestServerImpl();
    Naming.bind("//127.0.0.1/TestServer", dateS);
  }
}
