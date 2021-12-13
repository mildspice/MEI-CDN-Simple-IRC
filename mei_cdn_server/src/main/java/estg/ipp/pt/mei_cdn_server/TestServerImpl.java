package estg.ipp.pt.mei_cdn_server;

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
    System.setSecurityManager(new RMISecurityManager());
    TestServerImpl dateS = new TestServerImpl();
    Naming.bind("//127.0.0.1/DateServer", dateS);
  }
}
