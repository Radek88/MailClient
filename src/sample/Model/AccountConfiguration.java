package sample.Model;

import java.io.*;
import java.util.Properties;

public class AccountConfiguration{
    private  String user;
    private  String password;
    private  String hostForSend;
    private  String portForSend;
    private  String hostForCheckingMessages;
    private  String sslEnable;
    private  String tslEnable;
    private  String smtAuth ;


    public AccountConfiguration(){
        readConfigFile();
    }


    private void readConfigFile(){
        try {
            BufferedReader br = new BufferedReader(new FileReader("Config.txt"));
            user = br.readLine();
            password = br.readLine();
            hostForCheckingMessages = br.readLine();
            String portForCheckingMessages = br.readLine();
            hostForSend = br.readLine();
            portForSend = br.readLine();
            sslEnable = br.readLine();
            tslEnable = br.readLine();
            smtAuth = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public  Properties configuredProperties(){
        Properties properties = new Properties();
        properties.put("mail.smtp.starttls.enable", tslEnable);
        properties.put("mail.smtp.host", hostForSend);
        properties.put("mail.smtp.port", portForSend);
        properties.put("mail.smtp.ssl.enable", sslEnable);
        properties.put("mail.smtp.auth", smtAuth);
        properties.put("user",user);
        properties.put("password",password);

        return properties;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getHostForCheckingMessages() {
        return hostForCheckingMessages;
    }
}
