package sample.View.CustomizedJavaClasses;

import javafx.stage.Stage;

/**
 This class is adopted to use only with config window, singletone is implemented becouse
 of possibilities of closig config window after tap "Zapisz" button.
*/
public class StageConfigurationWinodw extends Stage{
    private static final StageConfigurationWinodw instance = new StageConfigurationWinodw();
    private StageConfigurationWinodw(){
        super();
    }
    public static StageConfigurationWinodw getInstace(){
        return instance;
    }
}
