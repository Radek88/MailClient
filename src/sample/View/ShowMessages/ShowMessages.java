package sample.View.ShowMessages;
import sample.View.MessageModel;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class ShowMessages{


    public Map<String, String> getFrom(MessageModel msg)  {
        Map<String, String> senderInformation = new HashMap<>();
        Address[] froms = msg.getFrom();
        if (froms.length < 1) {
            try {
                throw new MessagingException("Brak nadawcy");
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();

        if (person != null) {
            try {
                person = MimeUtility.decodeText(person) + " ";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            person = "";
        }

        senderInformation.put("name", person);
        senderInformation.put("emailAddress", address.getAddress());

        return senderInformation;
    }



}
