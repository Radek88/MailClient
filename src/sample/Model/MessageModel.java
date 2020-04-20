package sample.Model;
import lombok.Getter;
import javax.mail.Address;
import java.util.Date;

@Getter
public class MessageModel{


    private final String subject;
    private final Address[] from;
    private final Object content;
    private final Date receivedDate;

    public MessageModel(String subject, Address[] from, Object content, Date receivedDate) {
        this.subject = subject;
        this.from = from;
        this.content = content;
        this.receivedDate = receivedDate;
    }
}
