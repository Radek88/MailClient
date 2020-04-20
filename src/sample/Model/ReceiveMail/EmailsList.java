package sample.Model.ReceiveMail;

import javax.mail.Message;
import java.util.ArrayList;
import java.util.List;

public class EmailsList{
    private final List<Message> emailsStoreList = new ArrayList<>();

    public List<Message> getEmailsStoreList() {
        return emailsStoreList;
    }
}
