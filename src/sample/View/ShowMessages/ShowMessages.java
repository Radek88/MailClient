package sample.View.ShowMessages;

import sample.Model.MessageModel;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class ShowMessages{


    public List<MessageModel> returnListOfMessagesInFolder(Folder folder) {
        List<MessageModel> listOfMessagesInFolder = new ArrayList<>();
        try {
            for (Message m : folder.getMessages()
            ) {

                MessageModel message = new MessageModel(m.getSubject(), m.getFrom(), m.getContent(), m.getReceivedDate());

                listOfMessagesInFolder.add(message);
            }
        } catch (MessagingException | IOException e) {
            e.printStackTrace();

        }
        return listOfMessagesInFolder;
    }

    public Map<String, String> getFrom(Message msg) throws MessagingException,
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
    /*public String getDate(Date date){



    }*/


}
