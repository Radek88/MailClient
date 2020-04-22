package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import sample.Model.AccountConfiguration;
import sample.Model.SessionCreating;
import sample.View.ConfigWindow.ConfigurationWindow;
import sample.View.Folders.Folders;
import sample.View.ShowMessages.ShowMessages;

import javax.mail.Folder;
import javax.mail.Session;


public class Controller extends Thread{
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
    private ListView<String> listOfDates;
    @FXML
    private ListView<String> listOfSenders;
    @FXML
    private TextArea displayText;
    public static AccountConfiguration accountConfiguration = new AccountConfiguration();
    private static SessionCreating sessionCreating = new SessionCreating();
    public static Session session = sessionCreating.returnSession();
    private Folders folders = new Folders();
    private Thread choiceFolderThread;
    private ShowMessages showMessages = new ShowMessages();



    public Controller() {

    }

    public void initialize() {
        Platform.runLater(this::createView);
    }

    @FXML
    void configurationChoice(ActionEvent event) {
        ConfigurationWindow configurationWindow = new ConfigurationWindow();
        configurationWindow.showWindow();

    }

    @FXML
    void choiceFolderToShowContent(MouseEvent event) {

        if (event.getClickCount() == 2) {
            //
            //
            // problem with multi threads adding items from different folders to the same list!!!
            //
            //
            clearLists();
            choiceFolderThread = new Thread(() -> {

                String folderToShow = foldersView.getSelectionModel().getSelectedItem().getValue().getFullName();
                folders.showMessagesInTable(folderToShow);
            });
            choiceFolderThread.start();
        }
    }
    @FXML
    void displayMessage(MouseEvent event) {
        if(event.getClickCount() == 2){

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

    public ListView<String> getListOfDates() {
        return listOfDates;
    }

    public void setListOfDates(ListView<String> listOfDates) {
        this.listOfDates = listOfDates;
    }

    public ListView<String> getListOfSenders() {
        return listOfSenders;
    }

    public void setListOfSenders(ListView<String> listOfSenders) {
        this.listOfSenders = listOfSenders;
    }

    public TreeView<Folder> getFoldersView() {return foldersView;   }

    public TextArea getDisplayText() {
        return displayText;
    }

    public void setDisplayText(TextArea displayText) {
        this.displayText = displayText;
    }
}
