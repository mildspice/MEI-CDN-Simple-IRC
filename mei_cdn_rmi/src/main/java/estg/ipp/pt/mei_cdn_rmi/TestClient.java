package estg.ipp.pt.mei_cdn_rmi;

import java.util.Date;
import java.rmi.Naming;

public class TestClient {

	public String getDateServerString(String address) throws Exception {
	  
	  TestServer dateServer = 
		(TestServer)Naming.lookup(
		  "rmi://" + 
		  address +
		  "/TestServer"
		  );
	String txt = dateServer.getString(); // RMI
	return txt;	
	}

    public static void main (String args[]) throws Exception {
	if (args.length != 1)
	    throw new RuntimeException("Syntax:" + " TestClient <hostname>");

	TestServer dateServer = 
		(TestServer)Naming.lookup("rmi://" + 
				args[0] + "/TestServer");
	Date when = dateServer.getDate(); // RMI
	System.out.println(when);
	String txt = dateServer.getString(); // RMI
	System.out.println(txt);
    }
}
