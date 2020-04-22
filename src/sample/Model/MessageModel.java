package sample.Model;
import lombok.Getter;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import java.util.Date;

@Getter
public class MessageModel{


    private  String subject;
    private String description;
    private  Address[] from;
    private Address[] allRecipients;
    private Address[] replyTo;
    private Object content;
    private Date receivedDate;
    private Date sentDate;
    private Folder folder;
    private Flags flag;
    private String contentType;
    private int messageNumber;

    Message m;

    public MessageModel(String subject, Address[] from, Object content, Date receivedDate, Date senDate, Folder folder,
                        Address [] allRecipients, Flags flags, Address[] replyTo, String contentType, int messageNumber, String description)
    {
        this.subject = subject;
        this.from = from;
        this.content = content;
        this.receivedDate = receivedDate;
        this.sentDate = senDate;
        this.folder = folder;
        this.allRecipients = allRecipients;
        this.flag = flags;
        this.replyTo = replyTo;
        this.contentType = contentType;
        this.messageNumber = messageNumber;
        this.description = description;
    }

    public MessageModel(String subject, Address[] from, Object content, Date receivedDate) {

    }
}
