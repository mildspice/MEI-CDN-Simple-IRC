import java.rmi.Naming;

public class Client {

    public static void main (String args[]) throws Exception {
		String host = "localhost";
		if (args.length == 1) {
			host = args[0];
		} else {
			System.out.println("> No host was specified. Using 'localhost' as default ...");
		}

		AuthInterface auth = (AuthInterface) Naming.lookup("rmi://" + host + "/AuthServer");
		System.out.println("> Successfully connected to RMI registry: rmi://" + host + "/AuthServer");
		FileServerInterface fileServer = (FileServerInterface) Naming.lookup("rmi://" + host + "/FileServer");;
		ChatClient chat = new ChatClient();
		FileClient fileClient = new FileClient();
		//UnicastRemoteObject.exportObject(chat, 1099);
		System.out.println(auth.login("test", chat));

		System.out.println(auth.logout("test"));
    }
}
