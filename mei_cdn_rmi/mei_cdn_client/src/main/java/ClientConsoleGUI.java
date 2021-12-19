import java.rmi.RemoteException;
import java.util.Scanner;

public class ClientConsoleGUI {
    private static final Scanner scan = new Scanner(System.in);
    private static String name;

    public static boolean menu() {
        System.out.println();
        return false;
    }

    public static String login(AuthInterface rmiConn) {
        try {
            ChatClient chat = new ChatClient();
            System.out.print("> Hello there.\n> Your name, please: ");
            name = scan.next();
            System.out.println(rmiConn.login(name, chat) ? "> All good!" : "> Login failed, sorry ...");
            return name;
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean logout(AuthInterface rmiConn) {
        if (name == null || name.isEmpty()) return false;
        try {
            System.out.print("> Shure you want to logout (yes or no)?\n > ");
            String temp = scan.next();
            if (temp.equals("yes")) {
                boolean logout = rmiConn.logout(name);
                System.out.println(logout ? "> Goodbye!" : "> Something went wrong ...");
                return logout;
            } else {
                return false;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
