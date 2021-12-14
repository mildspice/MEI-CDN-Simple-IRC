import java.util.Date;
import java.rmi.Naming;

public class TestClient {

    public static void main (String args[]) throws Exception {
		String host = "localhost", rmi_class = "TestServer";
		if (args.length == 2) {
			host = args[0];
			rmi_class = args[1];
		}

		TestServer dateServer = (TestServer) Naming.lookup("rmi://" + host + "/" + rmi_class);
		Date when = dateServer.getDate(); // RMI
		System.out.println(when);
		String txt = dateServer.getString(); // RMI
		System.out.println(txt);
    }
}
