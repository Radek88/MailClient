package sample.View.ShowMessages;
import sample.Model.MessageModel;
import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class ShowMessages{


    public Map<String, String> getFrom(MessageModel msg) throws MessagingException,
            UnsupportedEncodingException {
        Map<String, String> senderInformation = new HashMap<>();
        Address[] froms = msg.getFrom();
        if (froms.length < 1) {
            throw new MessagingException("Brak nadawcy");
        }
        InternetAddress address = (InternetAddress) froms[0];
        String person = address.getPersonal();

        if (person != null) {
            person = MimeUtility.decodeText(person) + " ";
        } else {
            person = "";
        }

        senderInformation.put("name", person);
        senderInformation.put("emailAddress", address.getAddress());

        return senderInformation;
    }



}
