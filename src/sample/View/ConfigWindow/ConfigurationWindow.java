package sample.View.ConfigWindow;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.View.CustomizedJavaClasses.StageConfigurationWinodw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ConfigurationWindow{

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private VBox vbox;

    @FXML
    private TextField userName, password, incomingHost, incomingPort, outcomingHost, outcommingPort;

    @FXML
    private CheckBox sslEnable;
    String sslEnableString = "true";

    @FXML
    private CheckBox tslEnable;
    String tslEnableString = "true";

    @FXML
    private CheckBox authRequired;
    String authReqString = "true";
    @FXML
    private Button saveButton;
    private final ArrayList<String> listOfConfigurationAttributes = new ArrayList<>();

    //StageCustomized used to implement possibilities of closing config window using button from another class.
    //Window open from Controller and close from ConfigurationWindow.
    private final StageConfigurationWinodw configWindow = StageConfigurationWinodw.getInstace();


    public void setConfiguration() {
        if (!tslEnable.isSelected()) {
            tslEnableString = "false";
        }
        if (!sslEnable.isSelected()) {
            sslEnableString = "false";
        }
        if (!authRequired.isSelected()) {
            authReqString = "false";
        }

    }

    @FXML
    void mouseClicked(MouseEvent event) {
        createListOfConfigAttributes();
        saveConfigurationToFile();
        configWindow.close();


    }

    private void createListOfConfigAttributes() {
        setConfiguration();
        listOfConfigurationAttributes.add(userName.getText());
        listOfConfigurationAttributes.add(password.getText());
        listOfConfigurationAttributes.add(incomingHost.getText());
        listOfConfigurationAttributes.add(incomingPort.getText());
        listOfConfigurationAttributes.add(outcomingHost.getText());
        listOfConfigurationAttributes.add(outcommingPort.getText());
        listOfConfigurationAttributes.add(sslEnableString);
        listOfConfigurationAttributes.add(tslEnableString);
        listOfConfigurationAttributes.add(authReqString);
    }

    public void showWindow() {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("ConfigurationWindow.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        configWindow.setTitle("Configuration");
        configWindow.setScene(new Scene(root));
        configWindow.show();

    }


    public void saveConfigurationToFile() {
        File configFile = new File("Config.txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile))) {
            for (String s : listOfConfigurationAttributes
            ) {
                writer.write(s);
                writer.newLine();
            }
        } catch (IOException e) {
        }
    }


}

