import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageHistory {
    private final Map<String, List<Message>> userHistory = new HashMap<>();

    public MessageHistory() {}

    public void addUserMessage(String user, Message message){
        if(userHistory.containsKey(user)){
            userHistory.get(user).add(message);
        }else{
            userHistory.put(user, new ArrayList<>());
            userHistory.get(user).add(message);
        }
    }

    public List<Message> getUserHistory(String user){
        return userHistory.get(user); 
    }

    public synchronized void sendUserHistory(User user){  
        List<Message> userHistory = getUserHistory(user.getUsername());  
        if(userHistory!=null){  
        for (Message message : userHistory) {
            try {
                user.getChatClientInterface().chatMessage(message); 
            } catch (RemoteException e) {
                System.err.println("--- ERR\n> Error loading " + user.getUsername() + " history \n---");
            }
        }
    }
    }

}
