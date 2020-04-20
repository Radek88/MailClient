package sample.Model.ReceiveMail;

import sample.Model.AccountConfiguration;


import javax.mail.*;
import java.util.ArrayList;


public class ReceiveMail{
    private Session session;
    private EmailsList emailsList = new EmailsList();
    private ArrayList<Folder> listOfFolders;
    private final Store store = session.getStore("imaps");

    public ReceiveMail(AccountConfiguration accountConfiguration, Session session) throws NoSuchProviderException {
        this.session = session;
    }

    public void checkEmail() {
        try {



            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);


            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}


