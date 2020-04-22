package sample;

import sample.Model.AccountConfiguration;
import sample.Model.MessageModel;
import sun.swing.FilePane;

import javax.mail.*;
import java.io.*;
import java.util.*;

public class Content implements Serializable{
    AccountConfiguration accountConfiguration = Controller.accountConfiguration;
    Session session = Controller.session;
    Store store;
    public static Map<String, List<MessageModel>> listOfFoldersWithMessages = new HashMap<>();


    public void createContent() {
        try {
            store = session.getStore("imaps");
            store.connect(accountConfiguration.getHostForCheckingMessages(),
                    accountConfiguration.getUser(),
                    accountConfiguration.getPassword());
            List<Folder> list = Arrays.asList(store.getDefaultFolder().list("*"));
            System.out.println(list);
            for (Folder f : list
            ) {
                if (f.getType() !=0 && f.getType() != Folder.HOLDS_FOLDERS) {
                    store.getFolder(f.getFullName());
                    f.open(Folder.READ_ONLY);
                    mapFolderWithListOfMessages(f);
                }


            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

    private MessageModel createMessageModel(Message m) {
        MessageModel messageModel = null;
        try {
            messageModel = new MessageModel(m.getSubject(),
                    m.getFrom(),
                    m.getContent(),
                    m.getReceivedDate(),
                    m.getSentDate(),
                    m.getFolder(),
                    m.getAllRecipients(),
                    m.getFlags(),
                    m.getReplyTo(),
                    m.getContentType(),
                    m.getMessageNumber(),
                    m.getDescription());
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        return messageModel;
    }

    private void mapFolderWithListOfMessages(Folder folder) {
        try {
            ArrayList<MessageModel> listOfMessagesInFolder = new ArrayList<>();
            for (Message m : folder.getMessages()
            ) {
                MessageModel model = createMessageModel(m);
                listOfMessagesInFolder.add(model);
            }
            listOfFoldersWithMessages.put(folder.getFullName(), listOfMessagesInFolder);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    public void saveContentToFile(Object o){
        try {
            String filepath="C:\\Users\\RH\\Desktop\\WORKPLACE\\EmailClient";
            File file = new File(filepath);
            if(!file.exists()){
                file.createNewFile();
            }
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(o);
            objectOut.close();
            System.out.println("The Object  was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
