import java.rmi.Naming;

public class DummyClient {

    public static void main (String args[]) throws Exception {
		String host = "localhost", rmi_class = "DummyInterface";
		if (args.length == 2) {
			host = args[0];
			rmi_class = args[1];
		}

		DummyInterface dateServer = (DummyInterface) Naming.lookup("rmi://" + host + "/" + rmi_class);
		System.out.println("Connected to RMI registry: " + "rmi://" + host + "/" + rmi_class);

		System.out.println(dateServer.getDate());
    }
}
