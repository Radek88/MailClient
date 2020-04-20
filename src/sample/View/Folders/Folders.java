package sample.View.Folders;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import sample.Main;
import sample.Model.AccountConfiguration;
import sample.Model.SessionCreating;
import sample.View.ShowMessages.ShowMessages;

import javax.mail.*;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class Folders extends Thread{
    private final AccountConfiguration accountConfiguration = new AccountConfiguration();
    private final Session session = new SessionCreating().returnSession();
    private ArrayList<Folder> listOfFolders;
    private Store store;
    private final ShowMessages shmsg = new ShowMessages();
{
        try {
            store = session.getStore("imaps");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }
    private final Map<Integer, String> mapFoldersWithOriginalNames = new HashMap<>();


    public Folders() {
    }


    public ArrayList<Folder> returnListOfFolders() {
        listOfFolders = new ArrayList<>();
        try {
            connectToStore();
            createListOfFolders();
        } catch (MessagingException e) {
            e.getStackTrace();
        }
        listOfFolders.forEach(folder -> folder.getName().replaceAll("$/", ""));
        return listOfFolders;
    }

    private void createListOfFolders() throws MessagingException {
        listOfFolders.addAll(Arrays.asList(store.getDefaultFolder().list("%")));
    }


    private void connectToStore() throws MessagingException {
        store.connect(
                accountConfiguration.getHostForCheckingMessages(),
                accountConfiguration.getUser(),
                accountConfiguration.getPassword());
    }



    public Folder returnOpenFolder(String folderName) {
        Folder emailFolder = null;
        Store store = null;
        try {
            store = session.getStore("imaps");
            store.connect(
                    accountConfiguration.getHostForCheckingMessages(),
                    accountConfiguration.getUser(),
                    accountConfiguration.getPassword());
            emailFolder = store.getFolder(folderName);

            emailFolder.open(Folder.READ_ONLY);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return emailFolder;
    }
    /**
     * Platform.runLater() use do to fact all displayed nodes in java fx works on one main thread and modification is
     * impossible in parallel thread.
    **/
    public void buildViewOfFolder(String folderName, int numberOfMessagesOnPage)  {

        ArrayList<String> listOfSubjects = new ArrayList<>();
        ArrayList<Date> listOfDates1 = new ArrayList<>();
        ArrayList<String> listOfSenders1 = new ArrayList<>();

        try {
            Message[] messages = returnOpenFolder(folderName).getMessages();
            if (numberOfMessagesOnPage > messages.length) {
                numberOfMessagesOnPage = messages.length;
            }
            if (messages.length > 0) {
                for (int i = messages.length - 1; i > messages.length - (numberOfMessagesOnPage + 1); i--) {
                    Message m = messages[i];
                    listOfSubjects.add(m.getSubject());
                    listOfDates1.add(m.getSentDate());
                    listOfSenders1.add(shmsg.getFrom(m).get("name"));
                }
            } else {
                Platform.runLater(() -> {
                    Main.controller.getListOfMessages().getItems().add("Folder jest pusty");
                });
            }
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Platform.runLater(() -> {

            Main.controller.getListOfMessages().getItems().addAll(listOfSubjects);
            Main.controller .getListOfDates().getItems().addAll(listOfDates1);
            Main.controller.getListOfSenders() .getItems().addAll(listOfSenders1);

        });
    }

    public void createFoldersView() {
        TreeItem<Folder> rootItem =  new TreeItem<>();
        for (Folder folderObject : returnListOfFolders()
        ) {
            addFolderToView(rootItem, folderObject);
        }
        Main.controller.getFoldersView().setRoot(rootItem);
    }
    private void addFolderToView(TreeItem<Folder> rootItem, Folder folderObject) {
        TreeItem<Folder> folders = new TreeItem<>(folderObject, new ImageView(new Image("folderIcon.png")));

        try {
            getNextLevelOfFolderList(folderObject, folders);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        rootItem.getChildren().add(folders);
    }
    private void getNextLevelOfFolderList(Folder expandedFolder, TreeItem<Folder> item) throws MessagingException {

        for (Folder folderDownLevel : expandedFolder.list("%")) {
            TreeItem<Folder> item1 = new TreeItem<>(folderDownLevel, new ImageView(new Image("folderIcon.png")));
            if (folderDownLevel.list().length > 0) {
                getNextLevelOfFolderList(folderDownLevel, item1);
            }
            mapFoldersWithOriginalNames.put(item1.hashCode(), folderDownLevel.getName());
            item.getChildren().add(item1);
        }
    }
    public void showMessagesInTable(String folderName) {
        clearLists();
        int numberOfMessagesOnPage = 20;
        buildViewOfFolder(folderName, numberOfMessagesOnPage);


    }

    private void clearLists() {
        Main.controller.getListOfMessages().getItems().clear();
        Main.controller.getListOfDates().getItems().clear();
        Main.controller.getListOfSenders().getItems().clear();


    }


}
