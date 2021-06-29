package com.snorlax;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

/**
 * Class who controls the Login with Gmail credentials
 */
public class LoginController {
    @FXML
    private PasswordField pfPassword;
    @FXML
    private TextField tfEmail;

    @FXML
    protected void submitClicked(ActionEvent e) {
        System.out.println("SubmitClicked");
        String password = pfPassword.getText();
        String email = tfEmail.getText();
        if (!password.isBlank() && !email.isBlank()){
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(email, password);
                }
            };
            try {
                Session session = Session.getInstance(props, auth);
                Transport transport = session.getTransport("smtp");
                transport.connect(email, password);
                //App.setRoot("exampleDynamicCss");
                App.setRoot("index");
            } catch (AuthenticationFailedException failedException) {
                Alerts.showAlertMessage(Alert.AlertType.WARNING, "Error Log In", failedException.getMessage());
            } catch (NoSuchProviderException noSuchProviderException) {
                noSuchProviderException.printStackTrace();
            } catch (MessagingException messagingException) {
                messagingException.printStackTrace();
            } catch (NoClassDefFoundError error){
                Alerts.showAlertMessage(Alert.AlertType.ERROR, "Class not found!!", error.toString());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }


        }else{
            Alerts.showAlertMessage(Alert.AlertType.WARNING, "Error Log In", "Password or email are empty");
        }
    }
}
