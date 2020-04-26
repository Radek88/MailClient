package sample;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebView;
import sample.Model.AccountConfiguration;
import sample.Model.SendingMail.MessageCreating;
import sample.View.MessageModel;
import sample.Model.SessionCreating;
import sample.View.ConfigWindow.ConfigurationWindow;

import javax.mail.*;
import java.io.IOException;
import java.util.Date;


public class Controller extends Thread {
    @FXML
    private javafx.scene.layout.BorderPane BorderPane = new BorderPane();
    @FXML
    private MenuBar menuBar = new MenuBar();
    @FXML
    private Menu MenuBarFile = new Menu();
    @FXML
    private MenuItem menu_plik_konfiguracja_button = new MenuItem();
    @FXML
    private TableView<MessageModel> messageTable;
    @FXML
    private TableColumn<MessageModel, String> fromColumn;
    @FXML
    private TableColumn<MessageModel, String> subjectColumn;
    @FXML
    private TableColumn<MessageModel, Date> dateColumn;
    @FXML
    private WebView webView;
    @FXML
    private TextField fromAddresses;
    @FXML
    private TextField textSubject;
    @FXML
    private TextArea displayText;
    public static AccountConfiguration accountConfiguration = new AccountConfiguration();
    private static SessionCreating sessionCreating = new SessionCreating();
    public static Session session = sessionCreating.returnSession();
    private Content content = new Content();
    @FXML
    private ListView<String> listOfFolders;
    @FXML
    private Button newMessage;


    public void initialize() {
        SessionCreating createSession = new SessionCreating();
        content.createContent();
        constructViewOfFolder();
    }

    @FXML
    void configurationChoice(ActionEvent event) {
        ConfigurationWindow configurationWindow = new ConfigurationWindow();
        configurationWindow.showWindow();

    }

    private void constructViewOfFolder() {
        Content.listOfFoldersWithMessages.keySet().forEach(s -> listOfFolders.getItems().add(s));

    }

    @FXML
    void choiceFolder(MouseEvent event) {
        String folder = listOfFolders.getSelectionModel().getSelectedItem();
        ObservableList<MessageModel> list = Content.listOfFoldersWithMessages.get(folder);
        showTable(list);

    }

    private void showTable(ObservableList<MessageModel> list) {
        setCellValueFactory();
        messageTable.setItems(list);

    }

    private void setCellValueFactory() {
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        fromColumn.setCellValueFactory(new PropertyValueFactory<>("fromString"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("receivedDate"));
    }


    @FXML
    void choiceMessage(MouseEvent event) throws MessagingException, IOException {
        MessageModel userChoice = messageTable.getSelectionModel().getSelectedItem();
        if(userChoice != null){
        //Show from
        String from = userChoice.getFromString();
        fromAddresses.setText(from);
        //Show subject
        String subject = userChoice.getSubject();
        textSubject.setText(subject);
        // Show content
        Object messageContent = userChoice.getContent();
        StringBuffer message = content.convertMultiPartToString(messageContent);
        webView.getEngine().loadContent(message.toString());}
    }

    @FXML
    void createNewMessage(MouseEvent event) {
        MessageCreating createMessage = new MessageCreating();
        createMessage.showWindow();
    }




    public TextArea getDisplayText() {
        return displayText;
    }

    public void setDisplayText(TextArea displayText) {
        this.displayText = displayText;
    }
}
