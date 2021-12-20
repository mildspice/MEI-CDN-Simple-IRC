import java.io.Serializable;
import java.time.Instant;

public class Message implements Serializable {

    private String sender;
    private String message;
    private Instant date;
    private boolean isPM;
    private String serverFileName;

    public Message(String sender, String message, boolean isPM) {
        this.sender = sender;
        this.message = message;
        this.isPM = isPM;
        date = Instant.now();
        serverFileName = "";
    }

    public Message(String sender, String message, boolean isPM, String serverFileName) {
        this.sender = sender;
        this.message = message;
        this.isPM = isPM;
        date = Instant.now();
        this.serverFileName = serverFileName;
    }

    public String getSender(){
        return sender;
    }

    public String getMessage(){
        return message;
    }

    public boolean isPM(){
        return isPM;
    }

    public Instant getDate(){
        return date;
    }

    public boolean hasFile(){
        return !serverFileName.isEmpty();
    }

    public String getServerFileName(){
        return serverFileName;
    }

    @Override
    public String toString() {
        String construct;
        if(isPM){
            construct = "PM from " + sender + ": " + message;
        } else {
            construct = sender + ": " + message;
        }
        return construct;
    }

}
