package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Model.AccountConfiguration;
import sample.View.MessageModel;


import javax.mail.*;
import java.io.*;
import java.util.*;

public class Content implements Serializable {
    private AccountConfiguration accountConfiguration = Controller.accountConfiguration;
    private Session session = Controller.session;
    private Store store;

    public static Map<String, ObservableList<MessageModel>> listOfFoldersWithMessages = new HashMap<>();


    public void createContent() {
        try {
            store = session.getStore("imap");
            if (!store.isConnected()) {
                connectToStore();
            }
            List<Folder> list = Arrays.asList(store.getDefaultFolder().list("*"));
            System.out.println(list);
            for (Folder f : list
            ) {
                if (f.getType() != 0 && f.getType() != Folder.HOLDS_FOLDERS && f.exists()) {
                    store.getFolder(f.getFullName());
                    if (!f.isOpen()) {

                        f.open(Folder.READ_ONLY);

                    }
                    mapFolderWithListOfMessages(f);

                }
                f.close(true);
            }
        } catch (MessagingException e) {
            e.printStackTrace();

        } finally {

            try {
                store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }


    }

    private void connectToStore() throws MessagingException {
        store.connect(accountConfiguration.getHostForCheckingMessages(),
                accountConfiguration.getUser(),
                accountConfiguration.getPassword());
    }

    private MessageModel createMessageModel(Message message) {
        MessageModel messageModel = null;
        try {
            messageModel = new MessageModel(message.getSubject(),
                    message.getFrom(),
                    message.getContent(),
                    message.getReceivedDate(),
                    message.getSentDate(),
                    message.getFolder(),
                    message.getAllRecipients(),
                    message.getFlags(),
                    message.getReplyTo(),
                    message.getContentType(),
                    message.getMessageNumber(),
                    message.getDescription(),
                    message);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
        }
        return messageModel;
    }

    private void mapFolderWithListOfMessages(Folder folder) {
        try {
            ObservableList<MessageModel> listOfMessagesInFolder = FXCollections.observableArrayList();
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

    public StringBuffer convertMultiPartToString(Object content) throws MessagingException, IOException {
        StringBuffer messageContent = new StringBuffer();

        if (content instanceof Multipart) {

            Multipart multipart = (Multipart) content;

            for (int i = 0; i < multipart.getCount(); i++) {
                Part part = multipart.getBodyPart(i);

                if (part.isMimeType("text/html")) {
                    messageContent.append(part.getContent().toString());
                }

                if (part.isMimeType("image/png")) {

                }

            }
        }
        return messageContent;
    }
}
/*        public void saveContentToFile (Object o){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("contetnt.bin"));
            objectOutputStream.writeObject(o);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        }*/

