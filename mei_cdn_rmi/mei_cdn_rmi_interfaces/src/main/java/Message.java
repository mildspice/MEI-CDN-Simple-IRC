import java.io.Serializable;
import java.time.Instant;

public class Message implements Serializable{

    private String sender;
    private String message;
    private Instant date;
    private boolean isPM;

    public Message(String sender, String message, boolean isPM) {
        this.sender = sender;
        this.message = message;
        this.isPM = isPM;
        date = Instant.now();
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
