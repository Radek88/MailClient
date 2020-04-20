package sample.Model.SendingMail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class MessageCreating{
    public MessageCreating(Session session, String fromAddress, String receipientsAddress, String subject, String messageText, MimeBodyPart attachment) {
        this.session = session;
        this.fromAddress = fromAddress;
        this.receipientsAddress = receipientsAddress;
        this.subject = subject;
        this.messageText = messageText;
        this.attachment = attachment;
    }

    public MessageCreating(Session session, String fromAddress, String receipientsAddress, String subject, String messageText) {
        this.session = session;
        this.fromAddress = fromAddress;
        this.receipientsAddress = receipientsAddress;
        this.subject = subject;
        this.messageText = messageText;
    }

    private final Session session;
    private final String fromAddress;
    private final String receipientsAddress;
    private final String subject;
    private final String messageText;
    private MimeBodyPart attachment;

    public void sendMessage() {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddress));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receipientsAddress));
            message.setSubject(subject);
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(messageText, "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            if (attachment != null) {
                multipart.addBodyPart(attachment);
            }
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
