package sample.Model;

import lombok.Getter;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

@Getter
public class SessionCreating{

    private Session session;
    private final AccountConfiguration accountConfiguration = new AccountConfiguration();
    final Properties properties = accountConfiguration.configuredProperties();

    public void createSession() {

        session = Session.getInstance(properties, new Authenticator(){
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(properties.getProperty("user"), properties.getProperty("password"));
            }
        });

    }

    public Session returnSession(){
        createSession();
        return this.session;
    }
}
