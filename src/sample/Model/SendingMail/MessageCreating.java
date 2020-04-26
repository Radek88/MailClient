package sample.Model.SendingMail;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import sample.Controller;
import sample.Model.AccountConfiguration;
import sample.Model.SessionCreating;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MessageCreating{
    Session session = Controller.session;
    @FXML
    private TextField address;

    @FXML
    private TextField subject;

    @FXML
    private HTMLEditor messageTextHTML;
    private Stage newMessage = new Stage();

    @FXML
    private Button sendMessageButton;









    public void sendMessage() {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(AccountConfiguration.user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(address.getText()));
            message.setSubject(subject.getText());
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(messageTextHTML.getHtmlText(), "text/html");
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            /*if (attachment != null) {
                multipart.addBodyPart(attachment);
            }*/
            message.setContent(multipart);
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void showWindow() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("createMessage.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        newMessage.setTitle("Nowa wiadomość");
        newMessage.setScene(scene);
        newMessage.show();

    }

    public void sendMessageMethod(javafx.scene.input.MouseEvent mouseEvent) {
        sendMessage();
    }
}
