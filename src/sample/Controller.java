package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import sample.Model.AccountConfiguration;
import sample.Model.SessionCreating;
import sample.View.ConfigWindow.ConfigurationWindow;
import sample.View.Folders.Folders;
import sample.View.ShowMessages.ShowMessages;

import javax.mail.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class Controller extends Thread{
    public Controller() {

    }


    @FXML
    private javafx.scene.layout.BorderPane BorderPane = new BorderPane();
    @FXML
    private MenuBar menuBar = new MenuBar();
    @FXML
    private Menu MenuBarFile = new Menu();
    @FXML
    private MenuItem menu_plik_konfiguracja_button = new MenuItem();
    @FXML
    private TreeView<Folder> foldersView;
    @FXML
    private ListView<String> listOfMessages;
    @FXML
    private ListView<Date> listOfDates;
    @FXML
    private ListView<String> listOfSenders;

    private AccountConfiguration accountConfiguration = new AccountConfiguration();
    private Session session;
    private SessionCreating sessionCreating = new SessionCreating();
    private  Folders folders;


    private Thread choiceFolderThread;


    public void initialize() {
        folders = new Folders();
        createView();

    }

    @FXML
    void configurationChoice(ActionEvent event) {
        ConfigurationWindow configurationWindow = new ConfigurationWindow();
        configurationWindow.showWindow();

    }

    @FXML
    void choiceFolderToShowContent(MouseEvent event) {
        clearLists();
        if (event.getClickCount() == 2) {
            //
            //
            // problem with multi threads adding items from different folders to the same list!!!
            //
            //
            choiceFolderThread = new Thread(() -> {

                String folderToShow = foldersView.getSelectionModel().getSelectedItem().getValue().getFullName();
                folders.showMessagesInTable(folderToShow);
            });
            choiceFolderThread.start();
        }
    }

    @FXML
    private void createView() {

        folders.createFoldersView();
        folders.showMessagesInTable("INBOX");

    }


    private void clearLists() {
        listOfMessages.getItems().clear();
        listOfDates.getItems().clear();
        listOfSenders.getItems().clear();


    }

    public ListView<String> getListOfMessages() {
        return listOfMessages;
    }

    public void setListOfMessages(ListView<String> listOfMessages) {
        this.listOfMessages = listOfMessages;
    }

    public ListView<Date> getListOfDates() {
        return listOfDates;
    }

    public void setListOfDates(ListView<Date> listOfDates) {
        this.listOfDates = listOfDates;
    }

    public ListView<String> getListOfSenders() {
        return listOfSenders;
    }

    public void setListOfSenders(ListView<String> listOfSenders) {
        this.listOfSenders = listOfSenders;
    }

    public TreeView<Folder> getFoldersView() {
        return foldersView;
    }
}
