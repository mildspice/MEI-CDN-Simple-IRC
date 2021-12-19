public class User {
    private String username;
    private ChatClientInterface clientMsgHandler;

    public User(String username, ChatClientInterface clientMsgHandler) {
        this.username = username;
        this.clientMsgHandler = clientMsgHandler;
    }

    public String getUsername() {
        return username;
    }

    public ChatClientInterface getChatClientInterface() {
        return clientMsgHandler;
    }

    @Override
    public String toString() {
        return "{ username: " + username + ", msgHandler: " + clientMsgHandler + " }";
    }
}