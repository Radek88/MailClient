package sample.View;
import lombok.Getter;
import sample.View.ShowMessages.ShowMessages;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import java.io.Serializable;
import java.util.Date;

@Getter
public class MessageModel implements Serializable{


    private  String subject;
    private String description;
    private  Address[] from;
    private String fromString;
    private Address[] allRecipients;
    private Address[] replyTo;
    private Object content;
    private Date receivedDate;
    private Date sentDate;
    private Folder folder;
    private Flags flag;
    private String contentType;
    private int messageNumber;

    private Message message;
    private ShowMessages shwMsg = new ShowMessages();

    public MessageModel(String subject, Address[] from, Object content, Date receivedDate, Date senDate, Folder folder,
                        Address [] allRecipients, Flags flags, Address[] replyTo, String contentType, int messageNumber, String description, Message message)
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
        fromString = shwMsg.getFrom(this).get("name");
        this.message = message;
    }


    public Object getContent() {
        openFolder();
        return content;
    }

    public String getSubject() {
        openFolder();
        return subject;
    }

    public Address[] getFrom() {
        return from;
    }

    public String getFromString() {
        InternetAddress address = (InternetAddress) from[0];
        fromString = address.getPersonal() + " " + "<"+address.getAddress()+">";

        openFolder();
        return fromString;
    }


    private void openFolder(){
        try {
            if(!message.getFolder().isOpen()){
            message.getFolder().open(Folder.READ_ONLY);}
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private void closeFolder(){
        if(message.getFolder().isOpen()){
            try {
                message.getFolder().close(true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }

        }
    }
}
