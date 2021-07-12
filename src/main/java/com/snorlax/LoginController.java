package com.snorlax;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private Parent root;
    private Scene scene;
    private Stage stage;

    @FXML
    protected void submitClicked(ActionEvent e) {
        String password = pfPassword.getText();
        String email = tfEmail.getText();
        if (!password.isBlank() && !email.isBlank()){
            Properties props = new Properties();
            props.put("mail.smtp.host", Config.getProperty("mail.smtp.host")); //SMTP Host
            props.put("mail.smtp.port", Config.getProperty("mail.smtp.port")); //TLS Port
            props.put("mail.smtp.auth", Config.getProperty("mail.smtp.auth")); //enable authentication
            props.put("mail.smtp.starttls.enable", Config.getProperty("mail.smtp.starttls.enable")); //enable STARTTLS
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("index.fxml"));
                root  = loader.load();
                Controller controller = loader.getController();
                controller.setTransport(transport, session);
                stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (AuthenticationFailedException failedException) {
                Alerts.showAlertMessage(Alert.AlertType.WARNING, "Error" , "Authentication Failed\n" + failedException.getMessage());
            } catch (NoSuchProviderException noSuchProviderException) {
                Alerts.showAlertMessage(Alert.AlertType.ERROR, "Error", noSuchProviderException.getMessage());
            } catch (MessagingException messagingException) {
                Alerts.showAlertMessage(Alert.AlertType.ERROR, "Error", "Error sending the message\n" + messagingException.getMessage());
            } catch (NoClassDefFoundError error){
                Alerts.showAlertMessage(Alert.AlertType.ERROR, "Class not found!!", error.getMessage());
            } catch (IOException ioException) {
                Alerts.showAlertMessage(Alert.AlertType.ERROR, "An I/O exception has occurred", "Exception produced by failed or interrupted I/O operations" + ioException.getMessage());
            }
        }else{
            Alerts.showAlertMessage(Alert.AlertType.WARNING, "Error Log In", "Password or email are empty");
        }
    }
}
